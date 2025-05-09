package com.example.test2.common.util;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.test2.common.enums.CookieType;

import jakarta.servlet.http.Cookie;

@Component
public class CookieUtil {
  static public final int COOKIE_AGE = 5 * 60;

  public Cookie createCookie(CookieType type, String value, int age) {
    Cookie cookie = new Cookie(type.getName(), value);
    cookie.setMaxAge(age); // 1 minutes
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    cookie.setSecure(false); // No HTTPS for now
    return cookie;
  }
}
