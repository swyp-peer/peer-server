package com.example.peer.user.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class UserErrorResponseEntity {

    private int status;
    private String code;
    private String message;

    public static ResponseEntity<UserErrorResponseEntity> toResponseEntity(UserErrorCode e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(UserErrorResponseEntity.builder()
                        .status(e.getHttpStatus().value())
                        .code(e.name())
                        .message(e.getMessage())
                        .build()
                );
    }
}