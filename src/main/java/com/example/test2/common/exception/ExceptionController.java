package com.example.test2.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.Data;

@ControllerAdvice
public class ExceptionController {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionDTO> handleDefaultException(Exception e) {
    e.printStackTrace();
    var m = "error:" + e.getClass() + " | " + e.getMessage();
    return new ResponseEntity<>(new ExceptionDTO(m), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
