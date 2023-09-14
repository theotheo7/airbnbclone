package com.schoolproject.airbnbclone.dtos.user.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {

    private String token;

}
