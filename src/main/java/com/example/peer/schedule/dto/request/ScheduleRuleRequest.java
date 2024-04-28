package com.example.peer.schedule.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class ScheduleRuleRequest {

    private List<Boolean> mondayScheduleRule;

    private List<Boolean> tuesdayScheduleRule;

    private List<Boolean> wednesdayScheduleRule;

    private List<Boolean> thursdayScheduleRule;

    private List<Boolean> fridayScheduleRule;

    private List<Boolean> saturdayScheduleRule;

    private List<Boolean> sundayScheduleRule;
}
