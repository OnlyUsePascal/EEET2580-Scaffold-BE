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
import com.example.test2.common.util.JwtService;
import com.example.test2.user.User;
import com.example.test2.user.UserService;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthRequestFilter extends OncePerRequestFilter {
  @Autowired
  private UserService userService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
      throws ServletException, IOException, IllegalArgumentException {
    var cookies = request.getCookies();

    if (cookies != null) {
      // extract token
      String token = null;
      for (var ck : cookies) {
        if (ck.getName().equals(CookieType.AUTH.getName())) {
          token = ck.getValue();
          break;
        }
      }

      // valid token + no auth context
      if (token != null && JwtService.isTokenValid(token) &&
          SecurityContextHolder.getContext().getAuthentication() == null) {
        // extract profile
        String email = JwtService.extractEmail(token);
        String role = JwtService.extractRole(token);
        User user = userService.loadUserByUsername(email);

        // token for spring context
        var authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(role));
        var authToken = new UsernamePasswordAuthenticationToken(user, null, authorities);

        // Add extra details into the token,
        // in this case, we add the request information
        authToken.setDetails(new WebAuthenticationDetailsSource()
            .buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        System.out.println("we have cookies :) | " + authToken);
      }
    } else {
      System.out.println("no cookies :(");
    }
    filterChain.doFilter(request, response);
  }
}
