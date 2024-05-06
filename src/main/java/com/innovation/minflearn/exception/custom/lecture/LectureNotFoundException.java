package com.innovation.minflearn.exception.custom.lecture;

import com.innovation.minflearn.exception.ApplicationException;
import com.innovation.minflearn.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class LectureNotFoundException extends ApplicationException {

    public LectureNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.LECTURE_NOT_FOUND, ErrorCode.LECTURE_NOT_FOUND.getMessage());
    }
}
