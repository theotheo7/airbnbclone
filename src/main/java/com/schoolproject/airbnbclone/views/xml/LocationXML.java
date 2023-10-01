package com.schoolproject.airbnbclone.views.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.schoolproject.airbnbclone.models.Location;
import lombok.Data;

import java.io.Serializable;

@Data
public class LocationXML implements Serializable {

    @JacksonXmlProperty(localName = "Latitude")
    private final Double latitude;

    @JacksonXmlProperty(localName = "Longitude")
    private final Double longitude;

    @JacksonXmlProperty(localName = "City")
    private final String city;

    @JacksonXmlProperty(localName = "Address")
    private final String address;

    public LocationXML(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.city = location.getCity();
        this.address = location.getAddress();
    }

}
