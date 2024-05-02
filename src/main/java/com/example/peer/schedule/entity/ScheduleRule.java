package com.example.peer.schedule.entity;

import com.example.peer.schedule.dto.request.ScheduleRuleRequest;
import com.example.peer.user.entity.MentorDetail;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
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

    @ElementCollection
    @CollectionTable(name = "monday_schedule_rule", joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<LocalTime> mondayScheduleRule = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "tuesday_schedule_rule",joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<LocalTime> tuesdayScheduleRule = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "wednesday_schedule_rule",joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<LocalTime> wednesdayScheduleRule = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "thursday_schedule_rule",joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<LocalTime> thursdayScheduleRule = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "friday_schedule_rule",joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<LocalTime> fridayScheduleRule = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "saturday_schedule_rule",joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<LocalTime> saturdayScheduleRule = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "sunday_schedule_rule",joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<LocalTime> sundayScheduleRule = new ArrayList<>();

    @OneToOne(mappedBy = "scheduleRule")
    private MentorDetail mentorDetail;

    @Builder
    public ScheduleRule(List<LocalTime> mondayScheduleRule, List<LocalTime> tuesdayScheduleRule, List<LocalTime> wednesdayScheduleRule, List<LocalTime> thursdayScheduleRule,
                        List<LocalTime> fridayScheduleRule, List<LocalTime> saturdayScheduleRule, List<LocalTime> sundayScheduleRule, MentorDetail mentorDetail) {
        this.mondayScheduleRule = mondayScheduleRule;
        this.tuesdayScheduleRule = tuesdayScheduleRule;
        this.wednesdayScheduleRule = wednesdayScheduleRule;
        this.thursdayScheduleRule = thursdayScheduleRule;
        this.fridayScheduleRule = fridayScheduleRule;
        this.saturdayScheduleRule = saturdayScheduleRule;
        this.sundayScheduleRule = sundayScheduleRule;
        this.mentorDetail = mentorDetail;
        //==연관관계 메서드==//
        mentorDetail.UpdateScheduleRule(this);
    }

    public ScheduleRule UpdateScheduleRule(ScheduleRuleRequest scheduleRuleRequest) {
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
        return this;
    }
}
