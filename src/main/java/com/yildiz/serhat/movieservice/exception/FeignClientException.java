package com.yildiz.serhat.movieservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FeignClientException extends RuntimeException{

    private final HttpStatus httpStatus;

    public FeignClientException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
