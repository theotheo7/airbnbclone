package com.schoolproject.airbnbclone.exceptions;

import org.springframework.http.HttpStatus;

public class MessageException extends AirbnbCloneException {

    public static final String NOT_FOUND = "ME_001";

    public MessageException(String message, String code, HttpStatus httpStatus) {
        super(message, code, httpStatus);
    }

    public MessageException(String code, HttpStatus httpStatus) {
        super(code, httpStatus);
    }


}
