package com.example.test2.common.enums;

import lombok.*;

@AllArgsConstructor
@Getter
public enum CookieType {
  AUTH("auth"),
  SESSION("session");

  private String name;
}
