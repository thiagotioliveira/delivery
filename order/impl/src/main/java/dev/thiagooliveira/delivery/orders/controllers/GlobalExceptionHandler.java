package dev.thiagooliveira.delivery.orders.controllers;

import java.util.Map;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotImplementedException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(NotImplementedException ex) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
