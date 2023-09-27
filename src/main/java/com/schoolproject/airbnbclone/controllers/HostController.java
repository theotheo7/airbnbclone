package com.schoolproject.airbnbclone.controllers;

import com.schoolproject.airbnbclone.dtos.listing.response.ListingBasicDetails;
import com.schoolproject.airbnbclone.services.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/host")
@RequiredArgsConstructor
public class HostController {

    private final ListingService listingService;

    @RequestMapping(value = "/listings", method = RequestMethod.GET)
    public ResponseEntity<List<ListingBasicDetails>> getListings(Authentication authentication, @RequestParam("page") Integer page) {
        return ResponseEntity.ok(listingService.getListings(authentication, page-1));
    }

}
