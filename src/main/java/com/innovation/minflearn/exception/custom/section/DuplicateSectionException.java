package com.innovation.minflearn.exception.custom.section;

import com.innovation.minflearn.exception.ApplicationException;
import com.innovation.minflearn.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class DuplicateSectionException extends ApplicationException {

    public DuplicateSectionException() {
        super(HttpStatus.CONFLICT, ErrorCode.DUPLICATE_SECTION, ErrorCode.DUPLICATE_SECTION.getMessage());
    }
}
