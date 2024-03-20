package com.innovation.minflearn.exception.custom.lecture;

import com.innovation.minflearn.exception.ApplicationException;
import com.innovation.minflearn.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class InvalidExtensionException extends ApplicationException {

    public InvalidExtensionException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_EXTENSION, ErrorCode.INVALID_EXTENSION.getMessage());
    }
}
