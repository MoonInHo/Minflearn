package com.innovation.minflearn.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = (String) authentication.getCredentials();

        AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(email);

        if (!passwordEncoder.matches(password, accountContext.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        return new UsernamePasswordAuthenticationToken(accountContext, null, accountContext.getAuthorities());
    }

    public String generateAccessToken(Authentication authentication) {

        AccountContext accountContext = (AccountContext) authentication.getPrincipal();

        return jwtUtil.generateAccessToken(accountContext.getUsername(), accountContext.getMemberId());
    }

    public String generateRefreshToken() {
        return jwtUtil.generateRefreshToken();
    }

    public String reissueAccessToken(String authorizationHeader) {

        String accessToken = jwtUtil.resolveToken(authorizationHeader);
        String email = jwtUtil.extractEmail(accessToken);
        Long memberId = jwtUtil.extractMemberId(accessToken);

        return jwtUtil.generateAccessToken(email, memberId);
    }

    public Long extractMemberId(String authorizationHeader) {
        return jwtUtil.extractMemberId(jwtUtil.resolveToken(authorizationHeader));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(authentication);
    }
}
