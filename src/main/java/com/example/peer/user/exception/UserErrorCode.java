package com.example.peer.user.exception;

import com.example.peer.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다."),
    MENTOR_DETAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "멘토 정보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}