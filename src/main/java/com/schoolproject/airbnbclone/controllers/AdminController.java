package com.schoolproject.airbnbclone.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.schoolproject.airbnbclone.dtos.user.response.UserBasicDetails;
import com.schoolproject.airbnbclone.dtos.user.response.UserCompleteDetails;
import com.schoolproject.airbnbclone.services.ListingService;
import com.schoolproject.airbnbclone.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final ListingService listingService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserBasicDetails>> getUsers(@RequestParam("page") Integer page) {
        return ResponseEntity.ok(userService.getAllUsers(page-1));
    }

    @RequestMapping(value = "/users/approve/{username}", method = RequestMethod.PUT)
    public ResponseEntity<Void> approveUser(@PathVariable String username) {
        this.userService.approveHost(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/users/approve", method = RequestMethod.GET)
    public ResponseEntity<List<UserBasicDetails>> getHostsForApproval() {
        return ResponseEntity.ok(userService.getHostsForApproval());
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public ResponseEntity<UserCompleteDetails> getUser(@PathVariable String username) {
        return new ResponseEntity<>(this.userService.getUser(username), HttpStatus.OK);
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> export(@RequestParam(name = "json") Boolean asJSON) throws JsonProcessingException {
        return this.listingService.export(asJSON);
    }

}
