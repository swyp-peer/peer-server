package com.example.peer.schedule.exception;

import com.example.peer.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ScheduleErrorCode implements ErrorCode {
    SCHEDULE_RULE_NOT_FOUND(HttpStatus.BAD_REQUEST, "일정 규칙을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
