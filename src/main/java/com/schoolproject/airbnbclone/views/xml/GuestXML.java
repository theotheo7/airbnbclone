package com.schoolproject.airbnbclone.views.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.schoolproject.airbnbclone.models.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class GuestXML implements Serializable {

    @JacksonXmlProperty(localName = "Username")
    private final String username;

    @JacksonXmlProperty(localName = "First Name")
    private final String firstName;

    @JacksonXmlProperty(localName = "Last Name")
    private final String lastName;

    @JacksonXmlProperty(localName = "Email")
    private final String email;

    public GuestXML(User user) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }

}
