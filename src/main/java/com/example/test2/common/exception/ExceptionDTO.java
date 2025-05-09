package com.example.test2.common.exception;

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
