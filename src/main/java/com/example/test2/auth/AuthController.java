package com.example.test2.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test2.auth.dto.AuthDTO;
import com.example.test2.auth.dto.LoginDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/sign-up")
  public String register() {
    return "register";
  }

  @PostMapping("/sign-in")
  public AuthDTO signin(
      HttpServletRequest request, HttpServletResponse response,
      @RequestBody LoginDTO dto) throws Exception {
    Cookie cookie;
    try {
      cookie = authService.signin(dto);
    } catch (Exception e) {
      return new AuthDTO(e.getMessage());
    }

    response.addCookie(cookie);
    return new AuthDTO("Sign in successfully!");
  }

  @PostMapping("/sign-out")
  public String signout(HttpServletRequest request, HttpServletResponse response) {
    SecurityContextHolder.getContext().setAuthentication(null);
    return "signed out!";
  }

  @GetMapping("/me")
  public ResponseEntity<?> me(HttpServletRequest request) {
    var auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null || !auth.isAuthenticated()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
    }

    var principal = (UserDetails) auth.getPrincipal();
    List<String> roles = principal.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .toList();

    return ResponseEntity.ok(Map.of(
        "email", principal.getUsername(),
        "roles", roles));
  }

}
