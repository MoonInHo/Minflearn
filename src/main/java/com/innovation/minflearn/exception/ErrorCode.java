package com.innovation.minflearn.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Global
    INVALID_REQUEST("잘못된 요청"),

    // Member
    DUPLICATE_EMAIL("이미 사용중인 이메일 입니다."),
    DUPLICATE_PHONE("해당 연락처로 가입 정보가 존재합니다."),

    // Authorization
    EXPIRED_ACCESS_TOKEN("액세스 토큰이 만료 되었거나 존재하지 않습니다."),
    EXPIRED_REFRESH_TOKEN("리프레시 토큰이 존재하지 않거나 만료되었습니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}