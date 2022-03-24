package com.example.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .internalServerError()
                .body(e.getMessage());
    }
}
