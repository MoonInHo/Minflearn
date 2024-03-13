package com.innovation.minflearn.member.presentation.controller;

import com.innovation.minflearn.member.application.dto.response.TokenDto;
import com.innovation.minflearn.member.application.service.AuthService;
import com.innovation.minflearn.member.presentation.dto.request.SignInRequestDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authentication")
public class AuthRestController {

    public final String AUTHORIZATION_HEADER = "Authorization";
    public final String GRANT_TYPE = "Bearer";

    private final AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<Void> signIn(
            HttpServletResponse response,
            @RequestBody SignInRequestDto signInRequestDto,
            @CookieValue(name = "refresh_token", required = false) String refreshToken
    ) {
        TokenDto tokenDto = authService.signIn(signInRequestDto, refreshToken);

        setAuthorizationHeaderWithAccessToken(response, tokenDto);
        setRefreshTokenCookie(response, tokenDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-out")
    public ResponseEntity<Void> signOut(
            @CookieValue("refresh_token") String refreshTokenCookie
     ) {
        authService.signOut(refreshTokenCookie);

        return ResponseEntity.ok().build();
    }

    private void setAuthorizationHeaderWithAccessToken(HttpServletResponse response, TokenDto tokenDto) {
        response.setHeader(AUTHORIZATION_HEADER, GRANT_TYPE + " " + tokenDto.getAccessToken());
    }

    private void setRefreshTokenCookie(HttpServletResponse response, TokenDto tokenDto) {

        Cookie refreshTokenCookie = new Cookie("refresh_token", tokenDto.getRefreshToken());
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(1000 * 60 * 60 * 24 * 14);

        response.addCookie(refreshTokenCookie);
    }
}
