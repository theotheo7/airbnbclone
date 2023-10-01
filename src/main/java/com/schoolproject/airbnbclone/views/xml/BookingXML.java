package com.schoolproject.airbnbclone.views.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.schoolproject.airbnbclone.models.Booking;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class BookingXML implements Serializable {

    @JacksonXmlProperty(localName = "Guest")
    private final GuestXML guest;

    @JacksonXmlProperty(localName = "From")
    private final LocalDate from;

    @JacksonXmlProperty(localName = "To")
    private final LocalDate to;

    public BookingXML(Booking booking) {
        this.guest = new GuestXML(booking.getGuest());
        this.from = booking.getFromDate();
        this.to = booking.getToDate();
    }

}
