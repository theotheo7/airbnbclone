package com.schoolproject.airbnbclone.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AirbnbCloneException extends RuntimeException {

    private final String code;
    private final HttpStatus httpStatus;

    public AirbnbCloneException(String message, String code, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public AirbnbCloneException(String code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

}
