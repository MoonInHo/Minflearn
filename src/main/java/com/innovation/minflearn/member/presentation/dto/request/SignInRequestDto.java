package com.innovation.minflearn.member.presentation.dto.request;

public record SignInRequestDto(
        String email,
        String password
) {
}
