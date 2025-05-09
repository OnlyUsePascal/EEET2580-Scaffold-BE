package com.example.lab_test1.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CookieType {
  AUTH("auth"),
  SESSION("session");

  private String name;
}
