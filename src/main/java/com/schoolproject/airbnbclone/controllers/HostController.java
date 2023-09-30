package com.schoolproject.airbnbclone.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.schoolproject.airbnbclone.dtos.listing.request.ListingRequest;
import com.schoolproject.airbnbclone.dtos.listing.response.ListingBasicDetails;
import com.schoolproject.airbnbclone.dtos.listing.response.ListingCompleteDetails;
import com.schoolproject.airbnbclone.services.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping(value = "/listing/create", method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> createListing(
            Authentication authentication,
            @RequestParam(name = "listing") String listing,
            @RequestParam(name = "images", required = false) MultipartFile[] multipartFiles
            ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ListingRequest listingRequest = mapper.readValue(listing, ListingRequest.class);
        listingService.createListing(authentication, listingRequest, multipartFiles);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/listing/{id}", method = RequestMethod.GET)
    public ResponseEntity<ListingCompleteDetails> getListing(@PathVariable Long id) {
        return new ResponseEntity<>(this.listingService.getListing(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/listing", method = RequestMethod.PUT)
    public ResponseEntity<HttpStatus> updateListing(
            Authentication authentication,
            @RequestParam(name = "listing") String listing,
            @RequestParam(name = "images", required = false) MultipartFile[] multipartFiles
    ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ListingRequest listingRequest = mapper.readValue(listing, ListingRequest.class);
        listingService.updateListing(authentication, listingRequest, multipartFiles);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
