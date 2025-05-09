package com.example.test2.auth;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.test2.common.enums.CookieType;
import com.example.test2.common.util.JwtUtil;
import com.example.test2.user.User;
import com.example.test2.user.UserService;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthRequestFilter extends OncePerRequestFilter {
  private final UserService userService;
  private final JwtUtil jwtUtil;

  @Autowired
  public AuthRequestFilter(UserService userService, JwtUtil jwtUtil) {
    this.userService = userService;
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
      throws ServletException, IOException, IllegalArgumentException {
    var cookies = request.getCookies();

    if (cookies == null) {
      log.info("No cookies :(");
      filterChain.doFilter(request, response);
      return;
    }

    String token = null;
    for (var ck : cookies) {
      if (ck.getName().equals(CookieType.AUTH.getName())) {
        token = ck.getValue();
        break;
      }
    }
    if (token == null) {
      log.info("Cookies found but no token :(");
      filterChain.doFilter(request, response);
      return;
    }

    try {
      // extract profile
      String email = jwtUtil.extractEmail(token);
      String role = jwtUtil.extractRole(token);
      User user = userService.loadUserByUsername(email);

      // token for spring context
      var authorities = new ArrayList<SimpleGrantedAuthority>();
      authorities.add(new SimpleGrantedAuthority(role));
      var authToken = new UsernamePasswordAuthenticationToken(user, null, authorities);

      // Add extra details into the token, in this case, request information
      authToken.setDetails(new WebAuthenticationDetailsSource()
          .buildDetails(request));

      SecurityContextHolder.getContext().setAuthentication(authToken);

      System.out.println("we have token :) | " + authToken);
    } catch (Exception e) {
      log.warn("Authentication failed: {}", e.getMessage());
      throw new RuntimeException(e);
    }

    filterChain.doFilter(request, response);
  }
}
