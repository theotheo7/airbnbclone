package com.schoolproject.airbnbclone.dtos.listing.response;

import com.schoolproject.airbnbclone.models.Listing;
import lombok.Data;

import java.io.Serializable;

@Data
public class ListingBasicDetails implements Serializable {

    private String name;
    private String image;

    public ListingBasicDetails(Listing listing) {
        this.name = listing.getName();
        if (!listing.getImages().isEmpty()) {
            this.image = listing.getImages().iterator().next().getPath();
        } else {
            this.image = "";
        }
    }

}
