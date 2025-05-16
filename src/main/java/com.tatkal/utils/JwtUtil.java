package com.tatkal.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
      private String secret = "tatkal-two-point-o-signature-key"; // This should be in a properties file or stored as environment variable

      private final Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

  /*
       * This method is used to generate the token
       * Uses Hash based MAC to verify Integrity (i.e., to verify if the token is not tampered)
       * @param username
       * @return String
       */
      public String generateToken(String username){
        return Jwts.builder()
          .setSubject(username)
          .setIssuedAt(new Date())
          .setExpiration(new Date(System.currentTimeMillis() + 86400000))
          .signWith(key, SignatureAlgorithm.HS256)
          .compact();
      }

      public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
      }

      /*
       * This method is used to validate the token
       * Throws Exception if the token is invalid
       * @param token
       * @return boolean
       */
      public boolean validateToken(String token) {
        try {
          Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
          return true;
        } catch (Exception e) {
          return false;
        }
      }
}
