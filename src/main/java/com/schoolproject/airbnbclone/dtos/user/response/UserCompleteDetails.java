package com.schoolproject.airbnbclone.dtos.user.response;

import com.schoolproject.airbnbclone.models.Role;
import com.schoolproject.airbnbclone.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class UserCompleteDetails implements Serializable {

    private final String username;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final List<Role> roles;
    private final String image;
    private final boolean hostApproved;

    public UserCompleteDetails(User user) {

        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.roles = new ArrayList<>(user.getRoles());
        this.image = user.getImage().getPath();
        this.hostApproved = user.isHostApproved();

    }
}
