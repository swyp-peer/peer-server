package com.example.peer.schedule.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PossibleSchedulesResponse {

    private List<LocalDateTime> possibleSchedules;

    @Builder
    public PossibleSchedulesResponse(List<LocalDateTime> possibleSchedules) {
        this.possibleSchedules = possibleSchedules;
    }
}
