package com.innovation.minflearn.service;

import com.innovation.minflearn.dto.request.SignInRequestDto;
import com.innovation.minflearn.dto.response.TokenDto;
import com.innovation.minflearn.entity.RefreshTokenEntity;
import com.innovation.minflearn.exception.custom.auth.ExpiredRefreshTokenException;
import com.innovation.minflearn.repository.jpa.member.MemberRepository;
import com.innovation.minflearn.repository.redis.RefreshTokenRepository;
import com.innovation.minflearn.security.AccountContext;
import com.innovation.minflearn.security.JwtAuthProvider;
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
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public TokenDto signIn(SignInRequestDto signInRequestDto, String refreshTokenCookie) {

        deleteRefreshTokenIfExist(refreshTokenCookie);
        Authentication authentication = getAuthentication(signInRequestDto);

        String accessToken = jwtAuthProvider.generateAccessToken(authentication);
        String refreshToken = jwtAuthProvider.generateRefreshToken(getMemberId(authentication));

        refreshTokenRepository.save(new RefreshTokenEntity(refreshToken, getMemberId(authentication)));

        return new TokenDto(accessToken, refreshToken);
    }

    @Transactional
    public void signOut(String refreshTokenCookie) {

        checkRefreshTokenExist(refreshTokenCookie);

        refreshTokenRepository.deleteById(refreshTokenCookie);
    }

    @Transactional
    public TokenDto reissue(String refreshToken) {

        checkRefreshTokenExist(refreshToken);

        Long memberId = jwtAuthProvider.extractMemberId(refreshToken);
        String email = memberRepository.getEmail(memberId).email();

        String newAccessToken = jwtAuthProvider.reissueAccessToken(memberId, email);

        return new TokenDto(newAccessToken, null);
    }

    private void deleteRefreshTokenIfExist(String refreshTokenCookie) {
        if (isRefreshTokenExist(refreshTokenCookie)) {
            refreshTokenRepository.deleteById(refreshTokenCookie);
        }
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

    private void checkRefreshTokenExist(String refreshToken) {
        if (!isRefreshTokenExist(refreshToken)) {
            throw new ExpiredRefreshTokenException();
        }
    }

    private boolean isRefreshTokenExist(String refreshToken) {
        if (refreshToken == null) {
            return false;
        }
        return refreshTokenRepository.existsById(refreshToken);
    }
}
