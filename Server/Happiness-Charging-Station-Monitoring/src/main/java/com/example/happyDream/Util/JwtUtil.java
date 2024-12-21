package com.example.happyDream.Util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);  // 비밀 키 설정
    private final long expiration = 1000 * 60 * 60;  // 1시간 유효

    // JWT 발행 (username과 userId 포함)
    public String generateToken(String username, Integer userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);  // 사용자 이름
        claims.put("userId", userId); // 유저 식별자 (id)

        return Jwts.builder()
                .setClaims(claims)   // 커스텀 claims 설정
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)  // 비밀 키로 서명
                .compact();
    }

    // JWT 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // JWT에서 사용자 이름 추출
    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody().get("sub", String.class);  // sub claim에서 username 추출
    }

    // JWT에서 userId 추출
    public Integer extractUserId(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody().get("userId", Integer.class);  // userId 추출
    }
}
