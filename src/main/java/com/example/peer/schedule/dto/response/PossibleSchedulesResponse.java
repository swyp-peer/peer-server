package com.example.peer.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder
public class PossibleSchedulesResponse {

    private List<LocalDateTime> possibleSchedules;
}
