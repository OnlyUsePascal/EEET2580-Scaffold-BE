package com.example.lab_test1.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionController {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionDTO> handleDefaultException(Exception e) {
    e.printStackTrace();
    return new ResponseEntity<>(new ExceptionDTO(e.getMessage()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
 
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ExceptionDTO> handleNotFound(ResponseStatusException e) {
    e.printStackTrace();
    return new ResponseEntity<>(new ExceptionDTO(e.getReason()), e.getStatusCode());
  }
}
