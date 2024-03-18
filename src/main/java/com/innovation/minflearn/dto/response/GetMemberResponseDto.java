package com.innovation.minflearn.dto.response;

public record GetMemberResponseDto(
        Long id,
        String email,
        String password,
        String birthDate,
        String phone,
        String address,
        String addressDetail
) {
}
