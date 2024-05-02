package com.example.peer.schedule.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ScheduleErrorResponseEntity {
    private int status;
    private String code;
    private String message;

    public static ResponseEntity<ScheduleErrorResponseEntity> toResponseEntity(ScheduleErrorCode e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ScheduleErrorResponseEntity.builder()
                        .status(e.getHttpStatus().value())
                        .code(e.name())
                        .message(e.getMessage())
                        .build()
                );
    }
}
