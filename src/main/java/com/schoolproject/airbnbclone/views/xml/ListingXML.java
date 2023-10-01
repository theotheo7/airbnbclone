package com.schoolproject.airbnbclone.views.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.schoolproject.airbnbclone.models.Listing;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ListingXML implements Serializable {

    @JacksonXmlProperty(localName = "ListingID")
    private final Long id;

    @JacksonXmlProperty(localName = "Name")
    private final String name;

    @JacksonXmlProperty(localName = "Price")
    private final BigDecimal price;

    @JacksonXmlProperty(localName = "Summary")
    private final String summary;

    @JacksonXmlProperty(localName = "Location")
    private final LocationXML location;

    @JacksonXmlProperty(localName = "Host")
    private final HostXML host;

    @JacksonXmlProperty(localName = "Bookings")
    private final List<BookingXML> bookings;

    @JacksonXmlProperty(localName = "Reviews")
    private final List<ReviewXML> reviews;

    public ListingXML(Listing listing) {
        this.id = listing.getId();
        this.name = listing.getName();
        this.price = listing.getPrice();
        this.summary = listing.getSummary();
        this.location = new LocationXML(listing.getLocation());
        this.host = new HostXML(listing.getHost());
        this.bookings = listing.getBookings().stream()
                .map(BookingXML::new)
                .collect(Collectors.toList());
        this.reviews = listing.getReviews().stream()
                .map(ReviewXML::new)
                .collect(Collectors.toList());
    }

}
