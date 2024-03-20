package com.innovation.minflearn.exception.custom.lecture;

import com.innovation.minflearn.exception.ApplicationException;
import com.innovation.minflearn.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class MaxFileCountExceededException extends ApplicationException {

    public MaxFileCountExceededException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.MAX_FILE_COUNT_EXCEEDED, ErrorCode.MAX_FILE_COUNT_EXCEEDED.getMessage());
    }
}
