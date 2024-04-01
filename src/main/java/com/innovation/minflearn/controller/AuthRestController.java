package com.innovation.minflearn.controller;

import com.innovation.minflearn.dto.response.TokenDto;
import com.innovation.minflearn.service.AuthService;
import com.innovation.minflearn.dto.request.SignInRequestDto;
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
            @CookieValue(name = "refresh_token", required = false) String refreshTokenCookie
    ) {
        TokenDto tokenDto = authService.signIn(signInRequestDto, refreshTokenCookie);

        setAuthorizationHeaderWithAccessToken(response, tokenDto); //TODO JwtAuthProvider 에서 response Header 로 전달하는 방법 고민
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

    @PostMapping("/reissue")
    public ResponseEntity<Void> reissue(
            HttpServletResponse response,
            @CookieValue("refresh_token") String refreshToken
    ) {
        TokenDto tokenDto = authService.reissue(refreshToken);

        setAuthorizationHeaderWithAccessToken(response, tokenDto);

        return ResponseEntity.ok().build();
    }

    private void setAuthorizationHeaderWithAccessToken(HttpServletResponse response, TokenDto tokenDto) {
        response.setHeader(AUTHORIZATION_HEADER, GRANT_TYPE + " " + tokenDto.accessToken());
    }

    private void setRefreshTokenCookie(HttpServletResponse response, TokenDto tokenDto) {
        Cookie refreshTokenCookie = new Cookie("refresh_token", tokenDto.refreshToken());
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(1000 * 60 * 60 * 24 * 14);

        response.addCookie(refreshTokenCookie);
    }
}
