package com.ssafy.mvc.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JWTUtil {
    
    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),    Jwts.SIG.HS256.key().build().getAlgorithm());
    }
    
    // 아이디 검증
    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }
    
    // 역할 검증
    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }
    
    // 토큰이 소멸되었는지 확인
    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }
    
    // 토큰 생성시 다양한 데이터를 저장할 수 있음 (DTO or Map)
    public String createJwt(String username, String role, Long expiredMs) {
    
        return Jwts.builder().header().add("typ","JWT").and()
                .claim("username", username) // 아이디 클레임
                .claim("role", role) // 역할 클레임
                .issuedAt(new Date(System.currentTimeMillis())) // 토큰이 발급된 시간
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) // 토큰의 만료 시간
                .signWith(secretKey).compact();
      }
    
//    // 유효성 검증 
//    public Jws<Claims> vaildate(String token){
//        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
//    }
}