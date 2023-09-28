package com.schoolproject.airbnbclone.dtos.listing.request;

import com.schoolproject.airbnbclone.models.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ListingRequest implements Serializable {

    private Location location;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
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

}
