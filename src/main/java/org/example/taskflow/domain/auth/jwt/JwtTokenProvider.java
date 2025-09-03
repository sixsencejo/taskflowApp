package org.example.taskflow.domain.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.taskflow.domain.auth.config.JwtProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final SecretKey signingKey;
    private final JwtProperties jwtProperties;

    // SecretKey 한 번만 생성
    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.signingKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    // Access Token 생성
    public String generateAccessToken(String username, String role) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.getToken().getAccessExpiration());

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("type", "access")
                .issuedAt(now)
                .expiration(expiration)
                .signWith(signingKey)
                .compact();
    }

    // Token에서 Claims(사용자 정보) 추출
    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Token에서 사용자 이름 추출
    public String getUsername(String token) {
        return parseClaims(token).getSubject();
    }

    // Token에서 사용자 권한 추출
    public String getRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    // Token 유효성 검사
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Token이 Access인지 확인
    public boolean isAccessToken(String token) {
        return "access".equals(parseClaims(token).get("type", String.class));
    }
}
