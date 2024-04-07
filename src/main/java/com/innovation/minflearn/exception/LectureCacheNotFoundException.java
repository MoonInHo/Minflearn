package com.innovation.minflearn.exception;

import org.springframework.http.HttpStatus;

public class LectureCacheNotFoundException extends ApplicationException {

    public LectureCacheNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.LECTURE_CACHE_NOT_FOUND, ErrorCode.LECTURE_CACHE_NOT_FOUND.getMessage());
    }
}
