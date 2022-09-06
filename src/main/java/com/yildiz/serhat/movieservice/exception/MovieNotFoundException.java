package com.yildiz.serhat.movieservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MovieNotFoundException extends RuntimeException {
    private final HttpStatus httpStatus;

    public MovieNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
