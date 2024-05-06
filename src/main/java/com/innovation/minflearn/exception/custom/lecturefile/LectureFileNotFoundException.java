package com.innovation.minflearn.exception.custom.lecturefile;

import com.innovation.minflearn.exception.ApplicationException;
import com.innovation.minflearn.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class LectureFileNotFoundException extends ApplicationException {

    public LectureFileNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.LECTURE_FILE_NOT_FOUND, ErrorCode.LECTURE_FILE_NOT_FOUND.getMessage());
    }
}
