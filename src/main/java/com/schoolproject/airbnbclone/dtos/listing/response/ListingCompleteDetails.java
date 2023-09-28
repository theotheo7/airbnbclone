package com.schoolproject.airbnbclone.dtos.listing.response;

import com.schoolproject.airbnbclone.models.Image;
import com.schoolproject.airbnbclone.models.Listing;
import com.schoolproject.airbnbclone.models.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ListingCompleteDetails implements Serializable {

    private Location location;
    private String name;
    private Integer maxPeople;
    private Double price;
    private Integer extraPeople;
    private String type;
    private Integer beds;
    private Integer baths;
    private Integer meters;
    private Boolean living;
    private Boolean party;
    private Boolean pets;
    private String summary;
    private List<String> images;

    public ListingCompleteDetails(Listing listing) {
        this.location = listing.getLocation();
        this.name = listing.getName();
        this.maxPeople = listing.getMaxPeople();
        this.price = listing.getPrice();
        this.extraPeople = listing.getExtraPeople();
        this.type = listing.getType();
        this.beds = listing.getBeds();
        this.baths = listing.getBaths();
        this.meters = listing.getMeters();
        this.living = listing.getLiving();
        this.party = listing.getParty();
        this.pets = listing.getPets();
        this.summary = listing.getSummary();
        this.images = listing.getImages().stream()
                .map(Image::getPath)
                .toList();
    }

}
