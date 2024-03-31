package com.innovation.minflearn.dto.response;

public record TokenDto(
        String accessToken,
        String refreshToken
) {
}
