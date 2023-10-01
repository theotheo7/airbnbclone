package com.schoolproject.airbnbclone.views.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.schoolproject.airbnbclone.models.Listing;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ListingJSON implements Serializable {

    @JsonProperty("ListingID")
    private final Long id;

    @JsonProperty("Name")
    private final String name;

    @JsonProperty("Price")
    private final BigDecimal price;

    @JsonProperty("Summary")
    private final String summary;

    @JsonProperty("Location")
    private final LocationJSON location;

    @JsonProperty("Host")
    private final HostJSON host;

    @JsonProperty("Bookings")
    private final List<BookingJSON> bookings;

    @JsonProperty("Reviews")
    private final List<ReviewJSON> reviews;

    public ListingJSON(Listing listing) {
        this.id = listing.getId();
        this.name = listing.getName();
        this.price = listing.getPrice();
        this.summary = listing.getSummary();
        this.location = new LocationJSON(listing.getLocation());
        this.host = new HostJSON(listing.getHost());
        this.bookings = listing.getBookings().stream()
                .map(BookingJSON::new)
                .collect(Collectors.toList());
        this.reviews = listing.getReviews().stream()
                .map(ReviewJSON::new)
                .collect(Collectors.toList());
    }
}
