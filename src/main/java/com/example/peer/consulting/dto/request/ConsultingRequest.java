package com.example.peer.consulting.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConsultingRequest {

    private LocalDateTime consultingDateTime;

    private String message;

    private int managerCount;

    private int designerCount;

    private int frontendCount;

    private int backendCount;

    private Long mentorId;

    private String phoneNumber;

    @Builder
    public ConsultingRequest(LocalDateTime consultingDateTime, String message, int managerCount, int designerCount, int frontendCount, int backendCount, Long mentorId, String phoneNumber) {
        this.consultingDateTime = consultingDateTime;
        this.message = message;
        this.managerCount = managerCount;
        this.designerCount = designerCount;
        this.frontendCount = frontendCount;
        this.backendCount = backendCount;
        this.mentorId = mentorId;
        this.phoneNumber = phoneNumber;
    }
}
