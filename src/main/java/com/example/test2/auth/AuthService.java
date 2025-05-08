package com.example.test2.auth;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.test2.auth.dto.LoginDTO;
import com.example.test2.common.enums.CookieType;
import com.example.test2.common.util.CookieUtil;
import com.example.test2.common.util.JwtUtil;
import com.example.test2.user.UserService;

import jakarta.servlet.http.Cookie;

@Service
public class AuthService {
  private UserService userService;
  private CookieUtil cookieService;
  private JwtUtil jwtUtil;
  
  @Autowired
  public AuthService(UserService userService, CookieUtil cookieService, JwtUtil jwtUtil) {
    this.userService = userService;
    this.cookieService = cookieService;
    this.jwtUtil = jwtUtil;
  }

  public Cookie signin(LoginDTO dto) throws Exception {
    // user exists ?
    var user = userService.loadUserByUsername(dto.getEmail());

    if (!user.getPassword().equals(dto.getPassword()))
      throw new AuthenticationException("wrong password");

    // token for cookie
    return cookieService.createCookie(CookieType.AUTH, jwtUtil.createToken(user));
  }

  static public UserDetails getCurrentUser() {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    System.out.println(auth.isAuthenticated());
    if (auth == null || !auth.isAuthenticated()) {
      throw new AuthenticationCredentialsNotFoundException("No credentials found for current user");
    }
    return (UserDetails) auth.getPrincipal();
  }
}
