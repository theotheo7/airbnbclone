package com.schoolproject.airbnbclone.dtos.listing.response;

import com.schoolproject.airbnbclone.models.Listing;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ListingBasicDetails implements Serializable {

    private Long id;
    private String name;
    private String image;
    private BigDecimal price;
    private String type;
    private Integer beds;

    public ListingBasicDetails(Listing listing) {
        this.id = listing.getId();
        this.name = listing.getName();
        if (!listing.getImages().isEmpty()) {
            this.image = listing.getImages().iterator().next().getPath();
        } else {
            this.image = "";
        }
        this.price = listing.getPrice();
        this.type = listing.getType();
        this.beds = listing.getBeds();
    }

}
