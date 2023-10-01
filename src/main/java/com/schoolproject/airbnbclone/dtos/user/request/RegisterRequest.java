package com.schoolproject.airbnbclone.dtos.user.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RegisterRequest implements Serializable {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<Integer> roles;

}
