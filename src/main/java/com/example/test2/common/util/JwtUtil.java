package com.example.test2.common.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.test2.user.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
  private Long TOKEN_AGE = Long.valueOf(3600000);

  public String createToken(User user) {
    var claims = new DefaultClaims();
    // TODO: replace userdetails
    claims.put("role", user.getRole());

    return Jwts
        .builder()
        .setClaims(claims)
        .setSubject(user.getUsername()) // unique: email, username, or id
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + TOKEN_AGE))
        .signWith(getSecretKey())
        // .signWith(keyStoreManager.getPrivateKey(), SignatureAlgorithm.RS256)
        .compact();
  }

  // TODO: use key store
  private final String secretKey = "4c57f5bce709474cda66f67778ade103f84c81458cd98d33ad76e960f520462e";

  private SecretKey getSecretKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSecretKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public boolean isTokenValid(String token) {
    Claims claims;
    try {
      claims = extractAllClaims(token);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return claims.getExpiration().after(new Date());
  }

  public String extractEmail(String token) {
    return extractAllClaims(token).getSubject();
  }

  public String extractRole(String token) {
    return extractAllClaims(token).get("role", String.class);
  }
}
