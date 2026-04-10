package com.akv.service.controller.advice;

import com.akv.service.domain.exception.DeviceNotDeletableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //TODO: add swagger

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void handleNotFound() {
    }

    @ExceptionHandler(DeviceNotDeletableException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    void handleNotDeletable() {
    }

}
