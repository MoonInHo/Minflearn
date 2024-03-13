package com.innovation.minflearn.member.presentation.controller;

import com.innovation.minflearn.member.application.dto.response.TokenDto;
import com.innovation.minflearn.member.application.service.AuthService;
import com.innovation.minflearn.member.presentation.dto.request.SignInRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authentication")
public class AuthRestController {

    public final String AUTHORIZATION_HEADER = "Authorization";
    public final String GRANT_TYPE = "Bearer";

    private final AuthService authService;

    @PostMapping("/sign-in") //TODO 제대로 동작하는지 테스트, 테스트 코드 작성 / refresh token 사용 이유와 redis 에서 키값만으로 조회하지 않고 리프레쉬 토큰 코드가 따로 필요한 이유 고민
    public ResponseEntity<Void> signIn(
            HttpServletResponse response,
            @RequestBody SignInRequestDto signInRequestDto
    ) {
        TokenDto tokenDto = authService.signIn(signInRequestDto);

        setAuthorizationHeaderWithAccessToken(response, tokenDto);

        return ResponseEntity.ok().build();
    }

    private void setAuthorizationHeaderWithAccessToken(HttpServletResponse response, TokenDto tokenDto) {
        response.setHeader(AUTHORIZATION_HEADER, GRANT_TYPE + " " + tokenDto.getAccessToken());
    }
}
