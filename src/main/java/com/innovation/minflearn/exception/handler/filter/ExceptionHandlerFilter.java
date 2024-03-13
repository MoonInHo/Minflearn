package com.innovation.minflearn.exception.handler.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovation.minflearn.exception.ErrorCode;
import com.innovation.minflearn.exception.ErrorResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
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
            setAccessTokenErrorResponse(response);
        }
    }

    private void setAccessTokenErrorResponse(HttpServletResponse response) throws IOException {

        String result = objectMapper.writeValueAsString(
                new ErrorResponseDto(
                        ErrorCode.EXPIRED_ACCESS_TOKEN,
                        ErrorCode.EXPIRED_ACCESS_TOKEN.getMessage()
                )
        );
        log.error(ErrorCode.EXPIRED_ACCESS_TOKEN.name());

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(result);
    }
}
