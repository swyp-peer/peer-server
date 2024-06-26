package com.example.peer.schedule.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleRuleRequest {

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
