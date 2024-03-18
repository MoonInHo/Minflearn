package com.innovation.minflearn.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isInvalidAuthorization(authorization)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtUtil.resolveToken(authorization);

        if (jwtUtil.isExpired(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        String email = jwtUtil.extractEmail(accessToken);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, null, null);

        authenticationToken.setDetails(new WebAuthenticationDetailsSource() .buildDetails (request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

    private Boolean isInvalidAuthorization(String authorization) {
        return authorization == null || !authorization.startsWith("Bearer ");
    }
}
