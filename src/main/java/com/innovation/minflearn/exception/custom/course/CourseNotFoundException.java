package com.innovation.minflearn.exception.custom.course;

import com.innovation.minflearn.exception.ApplicationException;
import com.innovation.minflearn.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class CourseNotFoundException extends ApplicationException {

    public CourseNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.COURSE_NOT_FOUND, ErrorCode.COURSE_NOT_FOUND.getMessage());
    }
}
