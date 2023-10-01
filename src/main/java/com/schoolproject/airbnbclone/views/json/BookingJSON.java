package com.schoolproject.airbnbclone.views.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.schoolproject.airbnbclone.models.Booking;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class BookingJSON implements Serializable {

    @JsonProperty("Guest")
    private final GuestJSON guest;

    @JsonProperty("From")
    private final LocalDate from;

    @JsonProperty("To")
    private final LocalDate to;

    public BookingJSON(Booking booking) {
        this.guest = new GuestJSON(booking.getGuest());
        this.from = booking.getFromDate();
        this.to = booking.getToDate();
    }
}
