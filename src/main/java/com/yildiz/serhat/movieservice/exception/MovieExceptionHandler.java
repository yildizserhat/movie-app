package com.yildiz.serhat.movieservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class MovieExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<?> handleServiceExceptions(MovieNotFoundException exception) {
        log.error("Exception occurred: {}, httpStatus: {}", exception.getMessage(), exception.getHttpStatus());
        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(exception.getMessage());
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<?> handleNumberFormatException(NumberFormatException exception) {
        log.error("Exception occurred: {}, httpStatus: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
