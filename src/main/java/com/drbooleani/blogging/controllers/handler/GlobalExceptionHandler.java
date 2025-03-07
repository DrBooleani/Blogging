package com.drbooleani.blogging.controllers.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.drbooleani.blogging.services.exceptions.BadRequestException;
import com.drbooleani.blogging.services.exceptions.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<StandardError> handleValidationException(BindException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        StandardError error = new StandardError(
                "Validation Error", HttpStatus.BAD_REQUEST.value(), errors.toString(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        StandardError error = new StandardError(
                "Invalid argument type", HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleNotFoundException(ResourceNotFoundException ex) {
        StandardError error = new StandardError(
                "Not Found", HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardError> handleBadRequestException(BadRequestException ex) {
        StandardError error = new StandardError(
                "Bad Request", HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleException(Exception ex) {
        StandardError error = new StandardError(
                "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

record StandardError(
        String message,
        int code,
        String details,
        LocalDateTime timestamp
) {

}