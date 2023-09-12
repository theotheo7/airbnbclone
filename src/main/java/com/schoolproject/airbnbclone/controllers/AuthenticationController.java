package com.schoolproject.airbnbclone.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolproject.airbnbclone.services.AuthenticationService;
import com.schoolproject.airbnbclone.dtos.authentication.AuthenticationRequest;
import com.schoolproject.airbnbclone.dtos.authentication.AuthenticationResponse;
import com.schoolproject.airbnbclone.dtos.authentication.RegisterRequest;
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
            @RequestParam(name = "user") String user,
            @RequestParam(name = "image", required = false) MultipartFile multipartFile
            ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        RegisterRequest request = mapper.readValue(user, RegisterRequest.class);
        return ResponseEntity.ok(service.register(request, multipartFile));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
