package com.schoolproject.airbnbclone.views.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.schoolproject.airbnbclone.models.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class HostJSON implements Serializable {

    @JsonProperty("Username")
    private final String username;

    @JsonProperty("First Name")
    private final String firstName;

    @JsonProperty("Last Name")
    private final String lastName;

    @JsonProperty("Email")
    private final String email;

    @JsonProperty("Rating")
    private final Double hostRating;

    @JsonProperty("Reviews")
    private final List<ReviewJSON> reviews;

    public HostJSON(User user) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.hostRating = user.getHostRating();
        this.reviews = user.getHostReviews().stream()
                .map(ReviewJSON::new)
                .collect(Collectors.toList());
    }

}
