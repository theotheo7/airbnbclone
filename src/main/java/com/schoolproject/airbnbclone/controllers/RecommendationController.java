package com.schoolproject.airbnbclone.controllers;

import com.schoolproject.airbnbclone.dtos.listing.response.ListingBasicDetails;
import com.schoolproject.airbnbclone.services.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    /* Get User's Recommendations End-Point*/
    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public ResponseEntity<List<ListingBasicDetails>> recommend(Authentication authentication) {
        return new ResponseEntity<>(this.recommendationService.recommend(authentication), HttpStatus.OK);
    }

}
