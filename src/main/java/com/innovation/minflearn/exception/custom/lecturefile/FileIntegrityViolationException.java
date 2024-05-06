package com.innovation.minflearn.exception.custom.lecturefile;

import com.innovation.minflearn.exception.ApplicationException;
import com.innovation.minflearn.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class FileIntegrityViolationException extends ApplicationException {

    public FileIntegrityViolationException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY, ErrorCode.FILE_INTEGRITY_VIOLATION, ErrorCode.FILE_INTEGRITY_VIOLATION.getMessage());
    }
}
