package com.innovation.minflearn.exception.custom.course;

import com.innovation.minflearn.exception.ApplicationException;
import com.innovation.minflearn.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class CourseAccessDeniedException extends ApplicationException {

    public CourseAccessDeniedException() {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.COURSE_ACCESS_DENIED, ErrorCode.COURSE_ACCESS_DENIED.getMessage());
    }
}
