package com.schoolproject.airbnbclone.dtos.user.response;

import com.schoolproject.airbnbclone.models.Role;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {

    private String token;
    private List<Role> roles;

}
