package com.innovation.minflearn.exception.custom.member;

import com.innovation.minflearn.exception.ApplicationException;
import com.innovation.minflearn.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class DuplicatePhoneException extends ApplicationException {

    public DuplicatePhoneException() {
        super(HttpStatus.CONFLICT, ErrorCode.DUPLICATE_PHONE, ErrorCode.DUPLICATE_PHONE.getMessage());
    }
}
