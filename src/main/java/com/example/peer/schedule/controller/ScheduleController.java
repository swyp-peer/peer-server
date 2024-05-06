package com.example.peer.schedule.controller;

import com.example.peer.schedule.dto.request.ScheduleRuleRequest;
import com.example.peer.schedule.dto.response.PossibleSchedulesResponse;
import com.example.peer.schedule.dto.response.ScheduleRuleResponse;
import com.example.peer.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    /*
    멘토가 일정 규칙을 생성
     */
    @PostMapping("/mentor/create")
    public ResponseEntity<ScheduleRuleResponse> CreateScheduleRule(
            ScheduleRuleRequest scheduleRuleRequest,
            Long mentorId
    ) {
        return ResponseEntity.ok()
                .body(scheduleService.CreateScheduleRule(scheduleRuleRequest, mentorId));
    }

    /*
    멘토가 일정 규칙을 수정
     */
    @PutMapping("/mentor/update")
    public ResponseEntity<ScheduleRuleResponse> UpdateScheduleRule(
            ScheduleRuleRequest scheduleRuleRequest,
            Long mentorId
    ) {
        return ResponseEntity.ok()
                .body(scheduleService.UpdateScheduleRule(scheduleRuleRequest, mentorId));
    }

    /*
    멘토가 자신의 일정 규칙을 조회
     */
    @GetMapping("/mentor/view")
    public ResponseEntity<ScheduleRuleResponse> ViewMyScheduleRule(
        Long mentorId
    ) {
        return ResponseEntity.ok()
                .body(scheduleService.ViewScheduleRule(mentorId));
    }

    /*
    멘티가 멘토의 상담 가능 일정을 조회
     */
    @GetMapping("/mentee/view/{mentorId}")
    public ResponseEntity<PossibleSchedulesResponse> ViewPossibleSchedules(
            @PathVariable("mentorId") Long mentorId
    ) {
        return ResponseEntity.ok()
                .body(scheduleService.ViewPossibleSchedules(mentorId));
    }
}
