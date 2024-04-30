package com.example.peer.schedule.dto.response;

import com.example.peer.schedule.entity.ScheduleRule;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleRuleResponse {

    private List<LocalTime> mondayScheduleRule;

    private List<LocalTime> tuesdayScheduleRule;

    private List<LocalTime> wednesdayScheduleRule;

    private List<LocalTime> thursdayScheduleRule;

    private List<LocalTime> fridayScheduleRule;

    private List<LocalTime> saturdayScheduleRule;

    private List<LocalTime> sundayScheduleRule;

    @Builder
    public ScheduleRuleResponse(ScheduleRule scheduleRule) {
        this.mondayScheduleRule = scheduleRule.getMondayScheduleRule();
        this.tuesdayScheduleRule = scheduleRule.getTuesdayScheduleRule();
        this.wednesdayScheduleRule = scheduleRule.getWednesdayScheduleRule();
        this.thursdayScheduleRule = scheduleRule.getThursdayScheduleRule();
        this.fridayScheduleRule = scheduleRule.getFridayScheduleRule();
        this.saturdayScheduleRule = scheduleRule.getSaturdayScheduleRule();
        this.sundayScheduleRule = scheduleRule.getSundayScheduleRule();
    }
}
