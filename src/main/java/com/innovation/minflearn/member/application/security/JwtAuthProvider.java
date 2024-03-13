package com.innovation.minflearn.member.application.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
public class JwtAuthProvider {

    private final String SECRET_KEY;
    private final int ACCESS_TOKEN_EXPIRE;
    private final int REFRESH_TOKEN_EXPIRE;
    private static final String GRANT_TYPE = "Bearer";

    private final UserDetailsService userDetailsService;

    public JwtAuthProvider(
            @Value("${jwt.secret-key}") String secretKey,
            UserDetailsService userDetailsService
    ) {
        this.SECRET_KEY = secretKey;
        this.ACCESS_TOKEN_EXPIRE = 1000 * 60 * 30;
        this.REFRESH_TOKEN_EXPIRE = 1000 * 60 * 60 * 24 * 14;
        this.userDetailsService = userDetailsService;
    }

    public String generateAccessToken(Authentication authentication) {

        AccountContext accountContext = (AccountContext) authentication.getPrincipal();

        Claims claims = Jwts.claims();
        claims.put("email", accountContext.getUsername());
        claims.put("userId", accountContext.getMemberId());

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

    public static boolean isExpired(String token, String secretKey) {
        return extractClaims(token, secretKey)
                .getExpiration()
                .before(new Date());
    }

    public static String getUsername(String token, String secretKey) {
        return extractClaims(token, secretKey).get("username", String.class);
    }

    private static Claims extractClaims(String token, String secretKey) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
