package com.example.test2.auth;

import javax.naming.AuthenticationException;

import com.example.test2.user.User;
import com.example.test2.user.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.test2.auth.dto.LoginDTO;
import com.example.test2.common.enums.CookieType;
import com.example.test2.common.util.CookieUtil;
import com.example.test2.common.util.JwtUtil;
import com.example.test2.user.UserServiceImpl;

import jakarta.servlet.http.Cookie;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServiceImpl implements AuthService {
  private final UserServiceImpl userService;
  private final CookieUtil cookieUtil;
  private final JwtUtil jwtUtil;

  @Autowired
  public AuthServiceImpl(UserServiceImpl userService, CookieUtil cookieUtil, JwtUtil jwtUtil) {
    this.userService = userService;
    this.cookieUtil = cookieUtil;
    this.jwtUtil = jwtUtil;
  }

  public Cookie signIn(LoginDTO dto) throws Exception {
    // user exists ?
    var user = userService.loadUserByUsername(dto.getEmail());

    if (!user.getPassword().equals(dto.getPassword()))
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
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No credentials found for current user");
    }

    var principal = (User) auth.getPrincipal();
    return UserDTO.builder()
            .email(principal.getEmail())
            .name(principal.getName())
            .role(principal.getRole())
            .build();
  }
}
