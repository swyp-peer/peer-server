package com.example.peer.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public class BusinessException extends RuntimeException{

    private final ErrorCode errorCode;
}
