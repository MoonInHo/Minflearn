package com.innovation.minflearn.exception.custom.section;

import com.innovation.minflearn.exception.ApplicationException;
import com.innovation.minflearn.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class SectionNotFoundException extends ApplicationException {

    public SectionNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.SECTION_NOT_FOUND, ErrorCode.SECTION_NOT_FOUND.getMessage());
    }
}
