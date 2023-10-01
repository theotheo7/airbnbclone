package com.schoolproject.airbnbclone.dtos.message.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class MessagePosting implements Serializable {

    private final String title;
    private final String body;
    private final String recipient;

}
