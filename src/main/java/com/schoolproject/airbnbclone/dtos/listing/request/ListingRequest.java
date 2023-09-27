package com.schoolproject.airbnbclone.dtos.listing.request;

import com.schoolproject.airbnbclone.models.Location;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ListingRequest implements Serializable {

    private String name;
    private Location location;
    private LocalDate startDate;
    private LocalDate endDate;

    public ListingRequest(String name, Location location, String startDate, String endDate) {
        this.name = name;
        this.location = location;
        this.startDate = LocalDate.parse(startDate);
        this.endDate = LocalDate.parse(endDate);
    }

}
