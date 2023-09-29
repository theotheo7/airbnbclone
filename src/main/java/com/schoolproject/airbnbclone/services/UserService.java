package com.schoolproject.airbnbclone.services;

import com.schoolproject.airbnbclone.dtos.user.request.UserUpdate;
import com.schoolproject.airbnbclone.dtos.user.response.UserBasicDetails;
import com.schoolproject.airbnbclone.dtos.user.response.UserCompleteDetails;
import com.schoolproject.airbnbclone.exceptions.UserException;
import com.schoolproject.airbnbclone.models.Role;
import com.schoolproject.airbnbclone.models.User;
import com.schoolproject.airbnbclone.repositories.RoleRepository;
import com.schoolproject.airbnbclone.repositories.UserRepository;
import com.schoolproject.airbnbclone.dtos.user.request.UserLoginRequest;
import com.schoolproject.airbnbclone.dtos.user.response.UserLoginResponse;
import com.schoolproject.airbnbclone.dtos.user.request.RegisterRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ImageService imageService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public UserLoginResponse register(RegisterRequest request, MultipartFile multipartFile) {

        if (this.userRepository.existsByUsername(request.getUsername())) {
            throw new UserException(request.getUsername(), UserException.USER_USERNAME_EXISTS, HttpStatus.BAD_REQUEST);
        }

        if (this.userRepository.existsByEmail(request.getEmail())) {
            throw new UserException(request.getEmail(), UserException.USER_EMAIL_EXISTS, HttpStatus.BAD_REQUEST);
        }

        Set<Role> roles = roleRepository.findAllDistinct(request.getRoles());

        Role host = new Role(3, "HOST");

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .hostApproved(!roles.contains(host))
                .build();
        userRepository.save(user);
        this.imageService.uploadUserImage(user, multipartFile);
        var jwtToken = jwtService.generateToken(user);
        return UserLoginResponse.builder()
                .token(jwtToken)
                .roles(new ArrayList<>(user.getRoles()))
                .build();
    }

    public UserLoginResponse authenticate(UserLoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return UserLoginResponse.builder()
                .token(jwtToken)
                .roles(new ArrayList<>(user.getRoles()))
                .build();
    }

    @Transactional
    public void approveHost(String username) {
        if (this.userRepository.approveByUsername(username, true) == 0)
            throw new UserException(username, UserException.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public List<UserBasicDetails> getAllUsers(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<User> users = userRepository.findAll(pageable);
        List<User> userList = users.getContent();
        return userList.stream()
                .map(UserBasicDetails::new)
                .collect(Collectors.toList());
    }

    public UserCompleteDetails getUser(String username) {
        Optional<User> optionalUser = this.userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            return new UserCompleteDetails(optionalUser.get());
        } else {
            throw new UserException(username, UserException.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    public void updateProfile(Authentication authentication, UserUpdate userUpdate, MultipartFile multipartFile) {
        if (!authentication.getName().equals(userUpdate.getUsername())) {
            throw new UserException(UserException.USER_ACTION_FORBIDDEN, HttpStatus.BAD_REQUEST);
        }

        Optional<User> optionalUser = this.userRepository.findByUsername(authentication.getName());

        if (optionalUser.isEmpty()) {
            throw new UserException(UserException.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        System.out.println(userUpdate);

        User user = optionalUser.get();

        user.setUsername(userUpdate.getUsername());
        user.setEmail(userUpdate.getEmail());
        user.setFirstName(userUpdate.getFirstName());
        user.setLastName(userUpdate.getLastName());
        user.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        user.setPhoneNumber(userUpdate.getPhoneNumber());

        this.userRepository.save(user);

        this.imageService.updateUserImage(user, multipartFile);

    }

    public List<UserBasicDetails> getHostsForApproval() {
        return userRepository.findAllHostsForApproval();
    }

}
