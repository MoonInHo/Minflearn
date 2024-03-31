package com.innovation.minflearn.exception.custom.section;

import com.innovation.minflearn.exception.ApplicationException;
import com.innovation.minflearn.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class SectionLimitExceededException extends ApplicationException {

    public SectionLimitExceededException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.SECTION_LIMIT_EXCEEDED, ErrorCode.SECTION_LIMIT_EXCEEDED.getMessage());
    }
}
