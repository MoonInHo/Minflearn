package com.innovation.minflearn.exception.handler.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovation.minflearn.exception.ErrorCode;
import com.innovation.minflearn.exception.ErrorResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.error(ErrorCode.EXPIRED_ACCESS_TOKEN.name());
            setJwtFilterException(response, ErrorCode.EXPIRED_ACCESS_TOKEN, ErrorCode.EXPIRED_ACCESS_TOKEN.getMessage());
        } catch (SignatureException e) {
            log.error(ErrorCode.INVALID_SIGNATURE.name());
            setJwtFilterException(response, ErrorCode.INVALID_SIGNATURE, ErrorCode.INVALID_SIGNATURE.getMessage());
        }
    }

    private void setJwtFilterException(
            HttpServletResponse response,
            ErrorCode errorCode,
            String message
    ) throws IOException {

        String result = objectMapper.writeValueAsString(new ErrorResponseDto(errorCode, message));

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(result);
    }
}
