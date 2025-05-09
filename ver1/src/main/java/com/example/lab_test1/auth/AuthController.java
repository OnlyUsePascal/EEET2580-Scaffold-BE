package com.example.lab_test1.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lab_test1.auth.dto.AuthDTO;
import com.example.lab_test1.auth.dto.LoginRequest;
import com.example.lab_test1.user.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/sign-in")
  public AuthDTO signin(
      HttpServletRequest request, HttpServletResponse response,
      @RequestBody LoginRequest dto) throws Exception {
    var cookie = authService.signIn(dto);
    response.addCookie(cookie);
    return new AuthDTO("Sign in successfully!");
  }

  @PostMapping("/sign-out")
  public AuthDTO performLogout(HttpServletRequest request, HttpServletResponse response) {
    var cookie = authService.signOut(request);
    response.addCookie(cookie);
    return new AuthDTO("Sign out successfully");
  }

  @GetMapping("/me")
  public ResponseEntity<UserDTO> me(HttpServletRequest request) {
    return ResponseEntity.ok(authService.getMyProfile());
  }
}
