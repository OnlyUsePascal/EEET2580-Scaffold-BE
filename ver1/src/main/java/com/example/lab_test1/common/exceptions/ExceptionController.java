package com.example.lab_test1.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionDTO> handleDefaultException(Exception e) {
    e.printStackTrace();
    return new ResponseEntity<>(new ExceptionDTO(e.getMessage()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
