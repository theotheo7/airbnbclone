package com.schoolproject.airbnbclone.views.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.schoolproject.airbnbclone.models.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class HostXML implements Serializable {

    @JacksonXmlProperty(localName = "Username")
    private final String username;

    @JacksonXmlProperty(localName = "First Name")
    private final String firstName;

    @JacksonXmlProperty(localName = "Last Name")
    private final String lastName;

    @JacksonXmlProperty(localName = "Email")
    private final String email;

    @JacksonXmlProperty(localName = "Rating")
    private final Double hostRating;

    @JacksonXmlProperty(localName = "Reviews")
    private final List<ReviewXML> reviews;

    public HostXML(User user) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.hostRating = user.getHostRating();
        this.reviews = user.getHostReviews().stream()
                .map(ReviewXML::new)
                .collect(Collectors.toList());
    }

}
