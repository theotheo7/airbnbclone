package com.schoolproject.airbnbclone.controllers;

import com.schoolproject.airbnbclone.dtos.listing.response.ListingBasicDetails;
import com.schoolproject.airbnbclone.services.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}