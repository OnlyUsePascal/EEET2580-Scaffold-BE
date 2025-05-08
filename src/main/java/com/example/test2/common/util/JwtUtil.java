package com.example.test2.common.util;

import java.util.Date;
import java.util.HashMap;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.test2.user.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
  private final Long TOKEN_AGE;
  private final KeyUtil keyUtil;
  
  @Autowired
  public JwtUtil(KeyUtil keyUtil) {
    this.TOKEN_AGE = 3600000L;
    this.keyUtil = keyUtil;
  }

  public String createToken(UserDetails user) throws Exception{
    var claims = new HashMap<String, Object>();
    claims.put("role", ((User) user).getRole());

    var now = System.currentTimeMillis();

    return Jwts.builder()
        .claims()
        .subject(user.getUsername())
        .add(claims)
        .and()
        // .signWith(getSecretKey())
        .signWith(keyUtil.getPrivateKey())
        .issuedAt(new Date(now))
        .expiration(new Date(now + TOKEN_AGE))
        .compact();
  }

  // // TODO: use key store
  // private final String secretKey = "4c57f5bce709474cda66f67778ade103f84c81458cd98d33ad76e960f520462e";

  // private SecretKey getSecretKey() {
  //   byte[] keyBytes = Decoders.BASE64.decode(secretKey);
  //   return Keys.hmacShaKeyFor(keyBytes);
  // }

  private Claims extractAllClaims(String token) throws Exception{
    return Jwts.parser()
    // .verifyWith(getSecretKey())
    .verifyWith(keyUtil.getPublicKey())
    .build()
    .parseSignedClaims(token)
    .getPayload();
  }

  // public boolean isTokenValid(String token) {
  //   Claims claims;
  //   try {
  //     claims = extractAllClaims(token);
  //   } catch (Exception e) {
  //     e.printStackTrace();
  //     return false;
  //   }

  //   return claims.getExpiration().after(new Date());
  // }

  public String extractEmail(String token) throws Exception{
    return extractAllClaims(token).getSubject();
  }

  public String extractRole(String token) throws Exception{
    return extractAllClaims(token).get("role", String.class);
  }
}
