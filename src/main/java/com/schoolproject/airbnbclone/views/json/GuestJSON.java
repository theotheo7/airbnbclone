package com.schoolproject.airbnbclone.views.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.schoolproject.airbnbclone.models.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class GuestJSON implements Serializable {

    @JsonProperty("Username")
    private final String username;

    @JsonProperty("First Name")
    private final String firstName;

    @JsonProperty("Last Name")
    private final String lastName;

    @JsonProperty("Email")
    private final String email;

    public GuestJSON(User user) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }

}
