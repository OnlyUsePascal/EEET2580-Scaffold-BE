package com.example.test2.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
  USER("USER"),
  ADMIN("ADMIN"),
  STAFF("STAFF");
  
  private final String name;
}
