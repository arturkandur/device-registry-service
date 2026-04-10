package com.akv.service.controller.advice;

import com.akv.service.domain.exception.DeviceNotDeletableException;
import com.akv.service.domain.exception.DeviceNotUpdatableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void handleNotFoundStatus(RuntimeException e) {
        log.warn(e.getMessage(), e);
    }

    @ExceptionHandler({DeviceNotUpdatableException.class,
            DeviceNotDeletableException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    void handleConflictStatus(RuntimeException e) {
        log.error(e.getMessage(), e);
    }

}
