package com.schoolproject.airbnbclone.controllers;

import com.schoolproject.airbnbclone.dtos.listing.response.ListingBasicDetails;
import com.schoolproject.airbnbclone.dtos.listing.response.ListingCompleteDetails;
import com.schoolproject.airbnbclone.services.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final ListingService listingService;

    @RequestMapping(value = "/listings", method = RequestMethod.GET)
    public ResponseEntity<List<ListingBasicDetails>> searchListings(
            @RequestParam("page") Integer page,
            @RequestParam("city") String city,
            @RequestParam("from-date") String fDate,
            @RequestParam("to-date") String tDate,
            @RequestParam("people") Integer people,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "max-price", required = false) BigDecimal maxPrice,
            @RequestParam(value = "wifi", required = false) Boolean wifi,
            @RequestParam(value = "ac", required = false) Boolean ac,
            @RequestParam(value = "heat", required = false) Boolean heat,
            @RequestParam(value = "kitchen", required = false) Boolean kitchen,
            @RequestParam(value = "tv", required = false) Boolean tv,
            @RequestParam(value = "parking", required = false) Boolean parking,
            @RequestParam(value = "elevator", required = false) Boolean elevator
            ) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fromDate = LocalDate.parse(fDate, dtf);
        LocalDate toDate = LocalDate.parse(tDate, dtf);
        List<ListingBasicDetails> listingList = this.listingService.searchListings(page-1, city, fromDate, toDate, people, type, maxPrice, wifi, ac, heat, kitchen, tv, parking, elevator);
        return ResponseEntity.ok(listingList);
    }

    @RequestMapping(value = "/listing/{id}", method = RequestMethod.GET)
    public ResponseEntity<ListingCompleteDetails> getListingComplete(Authentication authentication, @PathVariable Long id) {
        return new ResponseEntity<>(this.listingService.getListing(authentication, id), HttpStatus.OK);
    }

}
