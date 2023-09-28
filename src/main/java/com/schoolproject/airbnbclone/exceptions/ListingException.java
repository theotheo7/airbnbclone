package com.schoolproject.airbnbclone.exceptions;

import org.springframework.http.HttpStatus;

public class ListingException extends AirbnbCloneException {

    public static final String LISTING_NOT_FOUND = "LE_001";

    public ListingException(String message, String code, HttpStatus httpStatus) {
        super(message, code, httpStatus);
    }

    public ListingException(String code, HttpStatus httpStatus) {
        super(code, httpStatus);
    }

}
