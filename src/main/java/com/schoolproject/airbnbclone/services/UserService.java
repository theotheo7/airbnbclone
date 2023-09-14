package com.schoolproject.airbnbclone.services;

import com.schoolproject.airbnbclone.dtos.user.response.UserBasicDetails;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ImageService imageService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public UserLoginResponse register(RegisterRequest request, MultipartFile multipartFile) {
        Set<Role> roles = roleRepository.findAllDistinct(request.getRoles());

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();
        repository.save(user);
        this.imageService.uploadUserImage(user, multipartFile);
        var jwtToken = jwtService.generateToken(user);
        return UserLoginResponse.builder()
                .token(jwtToken)
                .build();
    }

    public UserLoginResponse authenticate(UserLoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return UserLoginResponse.builder()
                .token(jwtToken)
                .build();
    }

    public List<UserBasicDetails> getAllUsers(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<User> users = repository.findAll(pageable);
        List<User> userList = users.getContent();
        return userList.stream()
                .map(UserBasicDetails::new)
                .collect(Collectors.toList());
    }

}
