package com.example.peer.consulting.exception;

import com.example.peer.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ConsultingErrorCode implements ErrorCode {
    CONSULTING_NOT_FOUND(HttpStatus.BAD_REQUEST, "상담을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
