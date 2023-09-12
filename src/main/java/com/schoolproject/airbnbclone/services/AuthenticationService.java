package com.schoolproject.airbnbclone.services;

import com.schoolproject.airbnbclone.models.Role;
import com.schoolproject.airbnbclone.models.User;
import com.schoolproject.airbnbclone.repositories.RoleRepository;
import com.schoolproject.airbnbclone.repositories.UserRepository;
import com.schoolproject.airbnbclone.dtos.authentication.AuthenticationRequest;
import com.schoolproject.airbnbclone.dtos.authentication.AuthenticationResponse;
import com.schoolproject.airbnbclone.dtos.authentication.RegisterRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ImageService imageService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request, MultipartFile multipartFile) {
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
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
