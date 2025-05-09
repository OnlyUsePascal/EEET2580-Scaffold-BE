package com.example.lab_test1.common.utils;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.lab_test1.user.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {
  private Long TOKEN_AGE;
  private KeyUtil keyUtil;

  @Autowired
  public JwtUtil(KeyUtil keyUtil) {
    this.TOKEN_AGE = Long.valueOf(3600000);
    this.keyUtil = keyUtil;
  }

  public String createToken(UserDetails user) throws Exception {
    var claims = new HashMap<String, Object>();
    claims.put("role", ((User) user).getRole());

    var now = System.currentTimeMillis();

    return Jwts.builder()
        .claims()
        .subject(user.getUsername())
        .add(claims)
        .and()
        .signWith(keyUtil.getPrivateKey())
        .issuedAt(new Date(now))
        .expiration(new Date(now + TOKEN_AGE))
        .compact();
  }

  private Claims extractAllClaims(String token) throws Exception {
    return Jwts.parser()
        .verifyWith(keyUtil.getPublicKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  public String extractEmail(String token) throws Exception {
    return extractAllClaims(token).getSubject();
  }

  public String extractRole(String token) throws Exception {
    return extractAllClaims(token).get("role", String.class);
  }
}
