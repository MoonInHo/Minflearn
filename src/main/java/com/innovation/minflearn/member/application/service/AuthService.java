package com.innovation.minflearn.member.application.service;

import com.innovation.minflearn.exception.custom.auth.ExpiredRefreshTokenException;
import com.innovation.minflearn.member.application.dto.response.TokenDto;
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

    private final JwtAuthProvider jwtAuthProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public TokenDto signIn(SignInRequestDto signInRequestDto, String refreshTokenCookie) {

        deleteRefreshTokenIfExist(refreshTokenCookie);
        Authentication authentication = getAuthentication(signInRequestDto);

        String accessToken = jwtAuthProvider.generateAccessToken(authentication);
        String refreshToken = jwtAuthProvider.generateRefreshToken();

        refreshTokenRepository.save(new RefreshToken(refreshToken, signInRequestDto.email()));

        return new TokenDto(accessToken, refreshToken);
    }

    @Transactional
    public void signOut(String refreshTokenCookie) {

        validateRefreshToken(refreshTokenCookie);

        refreshTokenRepository.deleteById(refreshTokenCookie);
    }

    @Transactional
    public TokenDto reissue(String authorizationHeader, String refreshTokenCookie) {

        validateRefreshToken(refreshTokenCookie);

        String newAccessToken = jwtAuthProvider.reissueAccessToken(authorizationHeader);

        return new TokenDto(newAccessToken, null);
    }

    private Authentication getAuthentication(SignInRequestDto signInRequestDto) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequestDto.email(),
                        signInRequestDto.password()
                )
        );
    }

    private void deleteRefreshTokenIfExist(String refreshTokenCookie) {
        if (isRefreshTokenExist(refreshTokenCookie)) {
            refreshTokenRepository.deleteById(refreshTokenCookie);
        }
    }

    private void validateRefreshToken(String refreshToken) {
        if (!isRefreshTokenExist(refreshToken)) {
            throw new ExpiredRefreshTokenException();
        }
    }

    private boolean isRefreshTokenExist(String refreshToken) {
        return refreshTokenRepository.existsById(refreshToken);
    }
}
