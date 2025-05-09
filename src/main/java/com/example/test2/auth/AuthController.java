package com.example.test2.auth;

import com.example.test2.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test2.auth.dto.AuthDTO;
import com.example.test2.auth.dto.LoginDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthServiceImpl authService;

  @Autowired
  public AuthController(AuthServiceImpl authService) {
    this.authService = authService;
  }

  @PostMapping("/sign-in")
  public ResponseEntity<AuthDTO> signin(
          HttpServletRequest request, HttpServletResponse response,
          @RequestBody LoginDTO dto) throws Exception {
    var cookie = authService.signIn(dto);
    response.addCookie(cookie);
    return ResponseEntity.ok(new AuthDTO("Sign in successfully!"));
  }

  @PostMapping("/sign-out")
  public ResponseEntity<AuthDTO>  performLogout(HttpServletRequest request, HttpServletResponse response) {
    var cookie = authService.signOut(request);
    response.addCookie(cookie);
    return ResponseEntity.ok(new AuthDTO("Sign out successfully"));
  }

  @GetMapping("/me")
  public ResponseEntity<UserDTO> me(HttpServletRequest request) {
    return ResponseEntity.ok(authService.getMyProfile());
  }
}