package com.schoolproject.airbnbclone.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolproject.airbnbclone.services.UserService;
import com.schoolproject.airbnbclone.dtos.user.request.UserLoginRequest;
import com.schoolproject.airbnbclone.dtos.user.response.UserLoginResponse;
import com.schoolproject.airbnbclone.dtos.user.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<UserLoginResponse> register(
            @RequestParam(name = "user") String user,
            @RequestParam(name = "image", required = false) MultipartFile multipartFile
            ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        RegisterRequest request = mapper.readValue(user, RegisterRequest.class);
        return ResponseEntity.ok(service.register(request, multipartFile));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserLoginResponse> authenticate(
            @RequestBody UserLoginRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
