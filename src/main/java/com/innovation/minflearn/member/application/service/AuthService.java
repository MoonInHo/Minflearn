package com.innovation.minflearn.member.application.service;

import com.innovation.minflearn.exception.custom.auth.ExpiredRefreshTokenException;
import com.innovation.minflearn.member.application.dto.response.TokenDto;
import com.innovation.minflearn.member.application.security.AccountContext;
import com.innovation.minflearn.member.application.security.JwtAuthProvider;
import com.innovation.minflearn.member.domain.entity.RefreshToken;
import com.innovation.minflearn.member.domain.repository.RefreshTokenRepository;
import com.innovation.minflearn.member.presentation.dto.request.SignInRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtAuthProvider jwtAuthProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenDto signIn(SignInRequestDto signInRequestDto, String refreshTokenCookie) {

        Authentication authentication = getAuthentication(signInRequestDto);

        Long memberId = getMemberId(authentication);
        if (isRefreshTokenExist(refreshTokenCookie)) {
            refreshTokenRepository.deleteById(refreshTokenCookie);
        }

        String accessToken = jwtAuthProvider.generateAccessToken(authentication);
        String refreshToken = jwtAuthProvider.generateRefreshToken();

        refreshTokenRepository.save(new RefreshToken(refreshToken, memberId));

        return new TokenDto(accessToken, refreshToken);
    }

    @Transactional
    public void signOut(String refreshTokenCookie) {

        validateRefreshTokenExistence(refreshTokenCookie);

        refreshTokenRepository.deleteById(refreshTokenCookie);
    }

    private Authentication getAuthentication(SignInRequestDto signInRequestDto) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequestDto.email(),
                        signInRequestDto.password()
                )
        );
    }

    private Long getMemberId(Authentication authentication) {
        AccountContext principal = (AccountContext) authentication.getPrincipal();
        return principal.getMemberId();
    }

    private void validateRefreshTokenExistence(String refreshToken) {
        if (!isRefreshTokenExist(refreshToken)) {
            throw new ExpiredRefreshTokenException();
        }
    }

    private boolean isRefreshTokenExist(String refreshToken) {
        return refreshTokenRepository.existsById(refreshToken);
    }
}
