package com.carvo.postii.postiiuser.controller;

import com.carvo.postii.postiiuser.service.exception.DataNotFoundException;
import com.carvo.postii.postiiuser.service.exception.DuplicatedDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(value = { DataNotFoundException.class })
    public ResponseEntity<String> handleNotFound(final Exception e) {
        log.debug("Not found", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(value = { DuplicatedDataException.class })
    public ResponseEntity<String> badRequest(final Exception e) {
        log.debug("Duplicated info not allowed", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
