package com.example.peer.schedule.entity;

import com.example.peer.schedule.dto.request.ScheduleRuleRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_create_rule_id")
    private Long id;

    private LocalDate scheduleStartDate;

    private LocalDate scheduleEndDate;

    @ElementCollection
    @CollectionTable(name = "monday_schedule_rule", joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<Boolean> mondayScheduleRule = new ArrayList<>(24);

    @ElementCollection
    @CollectionTable(name = "tuesday_schedule_rule",joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<Boolean> tuesdayScheduleRule = new ArrayList<>(24);

    @ElementCollection
    @CollectionTable(name = "wednesday_schedule_rule",joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<Boolean> wednesdayScheduleRule = new ArrayList<>(24);

    @ElementCollection
    @CollectionTable(name = "thursday_schedule_rule",joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<Boolean> thursdayScheduleRule = new ArrayList<>(24);

    @ElementCollection
    @CollectionTable(name = "friday_schedule_rule",joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<Boolean> fridayScheduleRule = new ArrayList<>(24);

    @ElementCollection
    @CollectionTable(name = "saturday_schedule_rule",joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<Boolean> saturdayScheduleRule = new ArrayList<>(24);

    @ElementCollection
    @CollectionTable(name = "sunday_schedule_rule",joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<Boolean> sundayScheduleRule = new ArrayList<>(24);

    @Builder
    public ScheduleRule(LocalDate scheduleStartDate, LocalDate scheduleEndDate) {
        this.scheduleStartDate = scheduleStartDate;
        this.scheduleEndDate = scheduleEndDate;
    }

    public void UpdateScheduleStartDate(LocalDate scheduleStartDate) {
        this.scheduleStartDate = scheduleStartDate;
    }

    public void UpdateScheduleEndDate(LocalDate scheduleEndDate) {
        this.scheduleEndDate = scheduleEndDate;
    }

    public void UpdateScheduleRule(ScheduleRuleRequest scheduleRuleRequest) {
        this.mondayScheduleRule.clear();
        this.mondayScheduleRule.addAll(scheduleRuleRequest.getMondayScheduleRule());

        this.tuesdayScheduleRule.clear();
        this.tuesdayScheduleRule.addAll(scheduleRuleRequest.getTuesdayScheduleRule());

        this.wednesdayScheduleRule.clear();
        this.wednesdayScheduleRule.addAll(scheduleRuleRequest.getWednesdayScheduleRule());

        this.thursdayScheduleRule.clear();
        this.thursdayScheduleRule.addAll(scheduleRuleRequest.getThursdayScheduleRule());

        this.fridayScheduleRule.clear();
        this.fridayScheduleRule.addAll(scheduleRuleRequest.getFridayScheduleRule());

        this.saturdayScheduleRule.clear();
        this.saturdayScheduleRule.addAll(scheduleRuleRequest.getSaturdayScheduleRule());

        this.sundayScheduleRule.clear();
        this.sundayScheduleRule.addAll(scheduleRuleRequest.getSundayScheduleRule());
    }
}
