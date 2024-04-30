package com.example.peer.schedule.controller;

import com.example.peer.schedule.dto.request.ScheduleRuleRequest;
import com.example.peer.schedule.dto.response.ScheduleRuleResponse;
import com.example.peer.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/create")
    public ResponseEntity<ScheduleRuleResponse> CreateScheduleRule(
            ScheduleRuleRequest scheduleRuleRequest,
            Long mentorId
    ) {
        return ResponseEntity.ok()
                .body(scheduleService.CreateScheduleRule(scheduleRuleRequest, mentorId));
    }


}
