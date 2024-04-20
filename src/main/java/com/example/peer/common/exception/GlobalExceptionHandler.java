package com.example.peer.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponseEntity> handleBusinessException(BusinessException e) {
        return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
    }
}
