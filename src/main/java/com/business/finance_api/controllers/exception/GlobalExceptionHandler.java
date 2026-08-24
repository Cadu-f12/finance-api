package com.business.finance_api.controllers.exception;

import com.business.finance_api.dto.exception.RequestErrorMessage;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<RequestErrorMessage> entityNotFound(EntityNotFoundException ex) {
        RequestErrorMessage response = new RequestErrorMessage(
                LocalDateTime.now(),
                404,
                "Not Found",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<RequestErrorMessage> entityAlreadyExists(EntityExistsException ex) {
        RequestErrorMessage response = new RequestErrorMessage(
                LocalDateTime.now(),
                409,
                "Conflit",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RequestErrorMessage> isNotFirstDayOfMonth(IllegalArgumentException ex) {
        RequestErrorMessage response = new RequestErrorMessage(
                LocalDateTime.now(),
                400,
                "Bad Request",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RequestErrorMessage> methodArgumentNotValid(MethodArgumentNotValidException ex) {
        RequestErrorMessage response = new RequestErrorMessage(
                LocalDateTime.now(),
                400,
                "Bad Request",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
