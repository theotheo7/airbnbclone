package com.schoolproject.airbnbclone.controllers;

import com.schoolproject.airbnbclone.services.AuthenticationService;
import com.schoolproject.airbnbclone.utils.AuthenticationRequest;
import com.schoolproject.airbnbclone.utils.AuthenticationResponse;
import com.schoolproject.airbnbclone.utils.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponse> register(
            @RequestPart(name = "user") RegisterRequest request,
            @RequestPart(name = "image", required = false) MultipartFile multipartFile
            ) {
        return ResponseEntity.ok(service.register(request, multipartFile));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
