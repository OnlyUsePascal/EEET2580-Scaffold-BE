package com.example.lab_test1.auth.dto;

import lombok.Data;

@Data
public class AuthDTO {
  String message;

  public AuthDTO() {
  }

  public AuthDTO(String message) {
    this.message = message;
  }
  
}
