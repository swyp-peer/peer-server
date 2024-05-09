package com.example.peer.schedule.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PossibleSchedulesResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private List<LocalDateTime> possibleSchedules;

    @Builder
    public PossibleSchedulesResponse(List<LocalDateTime> possibleSchedules) {
        this.possibleSchedules = possibleSchedules;
    }
}
