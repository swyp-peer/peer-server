package com.example.peer.schedule.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class ScheduleRuleRequest {

    private List<LocalTime> mondayScheduleRule;

    private List<LocalTime> tuesdayScheduleRule;

    private List<LocalTime> wednesdayScheduleRule;

    private List<LocalTime> thursdayScheduleRule;

    private List<LocalTime> fridayScheduleRule;

    private List<LocalTime> saturdayScheduleRule;

    private List<LocalTime> sundayScheduleRule;
}
