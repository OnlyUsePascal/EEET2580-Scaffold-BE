package com.example.test2.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class UserDTO {
  private String email;
  private String password;
  private String name;
}
