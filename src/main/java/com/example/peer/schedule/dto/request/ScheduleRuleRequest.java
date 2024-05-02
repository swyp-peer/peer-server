package com.example.peer.schedule.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleRuleRequest {

    private List<LocalTime> mondayScheduleRule;

    private List<LocalTime> tuesdayScheduleRule;

    private List<LocalTime> wednesdayScheduleRule;

    private List<LocalTime> thursdayScheduleRule;

    private List<LocalTime> fridayScheduleRule;

    private List<LocalTime> saturdayScheduleRule;

    private List<LocalTime> sundayScheduleRule;

    @Builder
    public ScheduleRuleRequest(List<LocalTime> mondayScheduleRule, List<LocalTime> tuesdayScheduleRule, List<LocalTime> wednesdayScheduleRule, List<LocalTime> thursdayScheduleRule, List<LocalTime> fridayScheduleRule, List<LocalTime> saturdayScheduleRule, List<LocalTime> sundayScheduleRule) {
        this.mondayScheduleRule = mondayScheduleRule;
        this.tuesdayScheduleRule = tuesdayScheduleRule;
        this.wednesdayScheduleRule = wednesdayScheduleRule;
        this.thursdayScheduleRule = thursdayScheduleRule;
        this.fridayScheduleRule = fridayScheduleRule;
        this.saturdayScheduleRule = saturdayScheduleRule;
        this.sundayScheduleRule = sundayScheduleRule;
    }
}
