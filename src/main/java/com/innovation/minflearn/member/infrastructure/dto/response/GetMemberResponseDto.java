package com.innovation.minflearn.member.infrastructure.dto.response;

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
