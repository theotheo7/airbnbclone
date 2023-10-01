package com.schoolproject.airbnbclone.dtos.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserUpdate implements Serializable {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;

}
