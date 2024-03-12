package com.innovation.minflearn.exception.custom.member;

import com.innovation.minflearn.exception.ApplicationException;
import com.innovation.minflearn.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends ApplicationException {

    public DuplicateEmailException() {
        super(HttpStatus.CONFLICT, ErrorCode.DUPLICATE_EMAIL, ErrorCode.DUPLICATE_EMAIL.getMessage());
    }
}
