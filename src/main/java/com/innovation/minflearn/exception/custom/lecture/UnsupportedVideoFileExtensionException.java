package com.innovation.minflearn.exception.custom.lecture;

import com.innovation.minflearn.exception.ApplicationException;
import com.innovation.minflearn.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class UnsupportedVideoFileExtensionException extends ApplicationException {

    public UnsupportedVideoFileExtensionException() {
        super(
                HttpStatus.BAD_REQUEST,
                ErrorCode.UNSUPPORTED_VIDEO_FILE_EXTENSION,
                ErrorCode.UNSUPPORTED_VIDEO_FILE_EXTENSION.getMessage()
        );
    }
}
