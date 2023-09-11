package com.schoolproject.airbnbclone.controllers;

import com.schoolproject.airbnbclone.dtos.listing.ListingRequest;
import com.schoolproject.airbnbclone.services.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/listing")
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void createListing(
            @RequestPart(name = "listing") ListingRequest listingRequest,
            @RequestPart(name = "images", required = false) MultipartFile[] multipartFiles
            ) {

        listingService.createListing(listingRequest, multipartFiles);

    }

    @DeleteMapping("/delete")
    public void deleteListing(
            @RequestBody Integer listingId
    ) {
        listingService.deleteListing(listingId);
    }

}
