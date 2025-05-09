package com.example.test2.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionDTO> handleDefaultException(Exception e) {
    e.printStackTrace();
    var m = "error:" + e.getClass() + " | " + e.getMessage();
    return new ResponseEntity<>(new ExceptionDTO(m), HttpStatus.INTERNAL_SERVER_ERROR);
  }
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
      return ResponseEntity.badRequest().body(ex.getMessage());
  }
}
