package com.example.lab_test1.common.exceptions;


public class ExceptionDTO {
  private String message;

  public ExceptionDTO() {
  }

  public ExceptionDTO(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
