package com.example.exemple.handler;

import com.example.exemple.model.error.ErrorMessage;
import com.example.exemple.model.exception.ResourceNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class RestExceptionHandler {

  public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception) {

    ErrorMessage errorMessage = new ErrorMessage("Not Found", HttpStatus.NOT_FOUND.value(), exception.getMessage());

    return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
  }
}
