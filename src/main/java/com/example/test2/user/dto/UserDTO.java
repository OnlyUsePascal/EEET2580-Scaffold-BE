package com.example.test2.user.dto;

import lombok.Builder;

@Builder
public class UserDTO {
  private String email;
  private String name;
  private String role;

  public UserDTO(String email, String name, String role) {
    this.email = email;
    this.name = name;
    this.role = role;
  }

  public UserDTO() {
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public String getRole() {
    return role;
  }


}
