package com.schoolproject.airbnbclone.controllers;

import com.schoolproject.airbnbclone.dtos.user.response.UserBasicDetails;
import com.schoolproject.airbnbclone.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserBasicDetails>> getUsers(@RequestParam("page") Integer page) {
        return ResponseEntity.ok(userService.getAllUsers(page-1));
    }

    @RequestMapping(value = "/users/approve/{username}", method = RequestMethod.PUT)
    public ResponseEntity<Void> approveUser(@PathVariable String username) {
        this.userService.approveHost(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
