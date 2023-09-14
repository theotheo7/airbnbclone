package com.schoolproject.airbnbclone.dtos.user.response;

import com.schoolproject.airbnbclone.models.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserBasicDetails implements Serializable {

    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String username;
    private final String image;

    public UserBasicDetails(User user) {

        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.username = user.getUsername();
        this.image = user.getImage().getPath();

    }

}
