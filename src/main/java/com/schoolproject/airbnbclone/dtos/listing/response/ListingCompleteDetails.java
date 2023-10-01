package com.schoolproject.airbnbclone.dtos.listing.response;

import com.schoolproject.airbnbclone.dtos.user.response.UserBasicDetails;
import com.schoolproject.airbnbclone.models.Image;
import com.schoolproject.airbnbclone.models.Listing;
import com.schoolproject.airbnbclone.models.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ListingCompleteDetails implements Serializable {

    private Long id;
    private Location location;
    private UserBasicDetails host;
    private String name;
    private Integer maxPeople;
    private BigDecimal price;
    private Integer extraPeople;
    private String type;
    private Integer beds;
    private Integer baths;
    private Integer meters;
    private Boolean living;
    private Boolean party;
    private Boolean pets;
    private String summary;
    private Boolean wifi;
    private Boolean ac;
    private Boolean heat;
    private Boolean kitchen;
    private Boolean tv;
    private Boolean parking;
    private Boolean elevator;
    private List<String> images;

    public ListingCompleteDetails(Listing listing) {
        this.id = listing.getId();
        this.location = listing.getLocation();
        this.host = new UserBasicDetails(listing.getHost());
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
        this.wifi = listing.getWifi();
        this.ac = listing.getAc();
        this.heat = listing.getHeat();
        this.kitchen = listing.getKitchen();
        this.tv = listing.getTv();
        this.parking = listing.getParking();
        this.elevator = listing.getElevator();
        this.images = listing.getImages().stream()
                .map(Image::getPath)
                .toList();
    }

}
