package com.example.lab_test1.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
  USER("USER"),
  ADMIN("ADMIN"),
  STAFF("STAFF");
  
  private String name;
}
