package com.example.peer.schedule.entity;

import com.example.peer.schedule.dto.request.ScheduleRuleRequest;
import com.example.peer.user.entity.MentorDetail;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class ScheduleRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_create_rule_id")
    private Long id;

    @ElementCollection
    @CollectionTable(name = "monday_schedule_rule", joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<LocalTime> mondayScheduleRule = new ArrayList<>(24);

    @ElementCollection
    @CollectionTable(name = "tuesday_schedule_rule",joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<LocalTime> tuesdayScheduleRule = new ArrayList<>(24);

    @ElementCollection
    @CollectionTable(name = "wednesday_schedule_rule",joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<LocalTime> wednesdayScheduleRule = new ArrayList<>(24);

    @ElementCollection
    @CollectionTable(name = "thursday_schedule_rule",joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<LocalTime> thursdayScheduleRule = new ArrayList<>(24);

    @ElementCollection
    @CollectionTable(name = "friday_schedule_rule",joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<LocalTime> fridayScheduleRule = new ArrayList<>(24);

    @ElementCollection
    @CollectionTable(name = "saturday_schedule_rule",joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<LocalTime> saturdayScheduleRule = new ArrayList<>(24);

    @ElementCollection
    @CollectionTable(name = "sunday_schedule_rule",joinColumns = @JoinColumn(name = "schedule_create_rule_id"))
    private List<LocalTime> sundayScheduleRule = new ArrayList<>(24);

    @OneToOne(mappedBy = "scheduleRule")
    private MentorDetail mentorDetail;

    @Builder
    public ScheduleRule() {
    }

    public void UpdateMentorDetail(MentorDetail mentorDetail) {
        this.mentorDetail = mentorDetail;
        //==연관관계 메서드==//
        mentorDetail.UpdateScheduleRule(this);
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
