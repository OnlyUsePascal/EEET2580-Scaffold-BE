package com.example.lab_test1.auth;

import com.example.lab_test1.auth.dto.LoginRequest;
import com.example.lab_test1.user.dto.UserDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;

public interface AuthService {
  public UserDTO getMyProfile();

  public Cookie signIn(LoginRequest dto) throws Exception;

  public Cookie signOut(@NotNull HttpServletRequest request);
}
