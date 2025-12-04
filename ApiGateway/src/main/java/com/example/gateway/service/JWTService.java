package com.example.gateway.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    public <T> T extractClaims(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.
                parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }


    public boolean isTokenValid(String token) {
           try{
               Jwts.parserBuilder()
                       .setSigningKey(getSignInKey())
                       .build()
                       .parseClaimsJws(token);

               return !isTokenExpired(token);
           } catch (JwtException e) {
               return  false;
           }
    }


    private boolean isTokenExpired(String token) {
             return  extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
         return  extractClaims(token, Claims :: getExpiration);
    }

    public String extractEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public Long extractId(String token){
        return  extractClaims(token, claims -> claims.get("id", Long.class) );
    }

    // ✨ Extract role
    public String extractRole(String token) {
        return extractClaims(token, claims -> claims.get("role", String.class));
    }

}

