package com.innovation.minflearn.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY;
    private final int ACCESS_TOKEN_EXPIRE;
    private final int REFRESH_TOKEN_EXPIRE;

    public JwtUtil(@Value("${jwt.secret-key}") String secretKey) {
        this.SECRET_KEY = secretKey;
        this.ACCESS_TOKEN_EXPIRE = 1000 * 60 * 30;
        this.REFRESH_TOKEN_EXPIRE = 1000 * 60 * 60 * 24 * 14;
    }

    public String generateAccessToken(String email, Long memberId) {

        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("memberId", memberId);
        //TODO role claim 추가 예정

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String generateRefreshToken() {

        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String resolveToken(String authorizationHeader) {

        String GRANT_TYPE = "Bearer";

        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(GRANT_TYPE)) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    public boolean isExpired(String token) {
        return extractClaims(token, SECRET_KEY)
                .getExpiration()
                .before(new Date());
    }

    public String extractEmail(String token) {
        return extractClaims(token, SECRET_KEY).get("email", String.class);
    }

    public Long extractMemberId(String token) {
        return extractClaims(token, SECRET_KEY).get("memberId", Long.class);
    }

    private Claims extractClaims(String token, String secretKey) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token) //TODO 액세스 토큰 만료시 reissue 메소드에서도 토큰 만료로 인해 접근 할 수 없는 문제 해결
                .getBody();
    }
}
