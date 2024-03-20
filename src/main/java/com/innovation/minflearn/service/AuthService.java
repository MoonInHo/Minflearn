package com.innovation.minflearn.service;

import com.innovation.minflearn.exception.custom.auth.ExpiredRefreshTokenException;
import com.innovation.minflearn.repository.jwt.RefreshTokenRepository;
import com.innovation.minflearn.dto.response.TokenDto;
import com.innovation.minflearn.security.JwtAuthProvider;
import com.innovation.minflearn.entity.RefreshTokenEntity;
import com.innovation.minflearn.dto.request.SignInRequestDto;
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

        refreshTokenRepository.save(new RefreshTokenEntity(refreshToken, signInRequestDto.email()));

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
