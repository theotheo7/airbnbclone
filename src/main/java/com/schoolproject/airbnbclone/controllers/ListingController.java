package com.schoolproject.airbnbclone.controllers;

import com.schoolproject.airbnbclone.dtos.listing.ListingRequest;
import com.schoolproject.airbnbclone.services.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/listing")
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;

    @PreAuthorize("hasRole('HOST')")
    @PostMapping("/create")
    public void createListing(
            @RequestBody ListingRequest listingRequest
            ) {
         listingService.createListing(listingRequest);
    }

    @DeleteMapping("/delete")
    public void deleteListing(
            @RequestBody Integer listingId
    ) {
        listingService.deleteListing(listingId);
    }

}
