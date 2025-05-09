package com.example.lab_test1.auth;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.lab_test1.auth.dto.LoginRequest;
import com.example.lab_test1.common.enums.CookieType;
import com.example.lab_test1.common.utils.CookieUtil;
import com.example.lab_test1.common.utils.JwtUtil;
import com.example.lab_test1.user.User;
import com.example.lab_test1.user.UserService;
import com.example.lab_test1.user.dto.UserDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;

@Service
public class AuthServiceImpl implements AuthService {
  private UserService userService;
  private CookieUtil cookieUtil;
  private JwtUtil jwtUtil;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public AuthServiceImpl(UserService userService, CookieUtil cookieUtil, JwtUtil jwtUtil,
      PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.cookieUtil = cookieUtil;
    this.jwtUtil = jwtUtil;
    this.passwordEncoder = passwordEncoder;
  }

  public Cookie signIn(LoginRequest dto) throws Exception {
    // user exists ?
    var user = userService.loadUserByUsername(dto.getEmail());

    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
      throw new AuthenticationException("Wrong password");

    // token for cookie
    return cookieUtil.createCookie(CookieType.AUTH, jwtUtil.createToken(user), CookieUtil.COOKIE_AGE);
  }

  public Cookie signOut(@NotNull HttpServletRequest request) {
    // clear session, context,
    var session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    SecurityContextHolder.getContext().setAuthentication(null);
    SecurityContextHolder.clearContext();
    return cookieUtil.createCookie(CookieType.AUTH, "sign-out", 0);
  }

  public UserDTO getMyProfile() {
    var auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null || !auth.isAuthenticated()) {
      throw new AuthenticationCredentialsNotFoundException("No credentials found for current user");
    }

    var principal = (User) auth.getPrincipal();
    return UserDTO.builder()
        .email(principal.getEmail())
        .name(principal.getName())
        .role(principal.getRole())
        .build();
  }
}
