package com.schoolproject.airbnbclone.views.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.schoolproject.airbnbclone.models.Location;
import lombok.Data;

import java.io.Serializable;

@Data
public class LocationJSON implements Serializable {

    @JsonProperty("Latitude")
    private final Double latitude;

    @JsonProperty("Longitude")
    private final Double longitude;

    @JsonProperty("City")
    private final String city;

    @JsonProperty("Address")
    private final String address;

    public LocationJSON(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.city = location.getCity();
        this.address = location.getAddress();
    }
}
