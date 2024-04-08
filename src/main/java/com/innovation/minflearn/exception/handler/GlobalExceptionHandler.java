package com.innovation.minflearn.exception.handler;

import com.innovation.minflearn.exception.ApplicationException;
import com.innovation.minflearn.exception.ErrorCode;
import com.innovation.minflearn.exception.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponseDto> onApplicationException(ApplicationException exception) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                exception.getErrorCode(),
                exception.getMessage()
        );

        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(errorResponseDto);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MissingRequestHeaderException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ErrorResponseDto> onIllegalRequestException(Exception exception) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                ErrorCode.INVALID_REQUEST,
                exception.getMessage()
        );

        return ResponseEntity
                .badRequest()
                .body(errorResponseDto);
    }

    //TODO IOException, NoSuchAlgorithmException 핸들링 추가
}
