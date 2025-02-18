package com.example.shoppingmall.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {
    // ✅ Base64 인코딩된 고정된 키 사용
    private static final String SECRET_KEY_STRING = "MySuperSecretKeyForJWTAuthentication123!"; // 256비트 이상
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(Base64.getEncoder().encode(SECRET_KEY_STRING.getBytes()));
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간 유효

    // 📌 JWT 토큰 생성
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // 사용자 정보 저장
                .setIssuedAt(new Date()) // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // ✅ 서명 키 일치
                .compact();
    }

    // 📌 JWT 토큰 검증 및 파싱
    public static String validateToken(String token) {
        try {
            System.out.println("📌 JWT 검증 시작: " + token);
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY) // ✅ 서명 키 일치
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            System.out.println("✅ JWT 검증 성공! 사용자: " + claims.getSubject());
            return claims.getSubject(); // 토큰에서 username 반환
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            System.out.println("❌ JWT 검증 실패: 만료된 토큰!");
        } catch (io.jsonwebtoken.SignatureException e) {
            System.out.println("❌ JWT 검증 실패: 서명이 올바르지 않음!");
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            System.out.println("❌ JWT 검증 실패: 잘못된 형식의 토큰!");
        } catch (Exception e) {
            System.out.println("❌ JWT 검증 실패: " + e.getMessage());
        }
        return null;
    }
}


