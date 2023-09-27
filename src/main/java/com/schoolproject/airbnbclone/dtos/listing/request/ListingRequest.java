package com.schoolproject.airbnbclone.dtos.listing.request;

import com.schoolproject.airbnbclone.models.Location;
import lombok.Data;

import java.io.Serializable;

@Data
public class ListingRequest implements Serializable {

    private String name;
    private Location location;

}
