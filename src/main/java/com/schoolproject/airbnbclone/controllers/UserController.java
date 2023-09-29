package com.schoolproject.airbnbclone.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolproject.airbnbclone.dtos.user.request.UserUpdate;
import com.schoolproject.airbnbclone.dtos.user.response.UserCompleteDetails;
import com.schoolproject.airbnbclone.services.UserService;
import com.schoolproject.airbnbclone.dtos.user.request.UserLoginRequest;
import com.schoolproject.airbnbclone.dtos.user.response.UserLoginResponse;
import com.schoolproject.airbnbclone.dtos.user.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<UserLoginResponse> register(
            @RequestParam(name = "user") String user,
            @RequestParam(name = "image", required = false) MultipartFile multipartFile
            ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        RegisterRequest request = mapper.readValue(user, RegisterRequest.class);
        return ResponseEntity.ok(userService.register(request, multipartFile));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserLoginResponse> authenticate(
            @RequestBody UserLoginRequest request
    ) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<UserCompleteDetails> getMyProfile(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateProfile(
            Authentication authentication,
            @RequestParam(name = "user") String userUpdate,
            @RequestParam(name = "image", required = false) MultipartFile multipartFile
            ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        UserUpdate user = mapper.readValue(userUpdate, UserUpdate.class);
        userService.updateProfile(authentication, user, multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
