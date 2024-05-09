package com.example.peer.schedule.dto.response;

import com.example.peer.schedule.entity.ScheduleRule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleRuleResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm")
    private List<LocalTime> mondayScheduleRule;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm")
    private List<LocalTime> tuesdayScheduleRule;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm")
    private List<LocalTime> wednesdayScheduleRule;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm")
    private List<LocalTime> thursdayScheduleRule;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm")
    private List<LocalTime> fridayScheduleRule;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm")
    private List<LocalTime> saturdayScheduleRule;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm")
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
