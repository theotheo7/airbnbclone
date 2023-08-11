package com.schoolproject.airbnbclone.dtos.listing;

import com.schoolproject.airbnbclone.models.Location;
import lombok.Data;

@Data
public class ListingRequest {

    private String name;
    private Location location;

}
