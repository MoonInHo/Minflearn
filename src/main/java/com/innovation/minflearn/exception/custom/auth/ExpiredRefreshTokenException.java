package com.innovation.minflearn.exception.custom.auth;

import com.innovation.minflearn.exception.ApplicationException;
import com.innovation.minflearn.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class ExpiredRefreshTokenException extends ApplicationException {

    public ExpiredRefreshTokenException() {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.EXPIRED_REFRESH_TOKEN, ErrorCode.EXPIRED_REFRESH_TOKEN.getMessage());
    }
}
