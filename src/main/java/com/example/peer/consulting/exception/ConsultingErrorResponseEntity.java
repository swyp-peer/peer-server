package com.example.peer.consulting.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ConsultingErrorResponseEntity {
    private int status;
    private String code;
    private String message;

    public static ResponseEntity<ConsultingErrorResponseEntity> toResponseEntity(ConsultingErrorCode e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ConsultingErrorResponseEntity.builder()
                        .status(e.getHttpStatus().value())
                        .code(e.name())
                        .message(e.getMessage())
                        .build()
                );
    }
}
