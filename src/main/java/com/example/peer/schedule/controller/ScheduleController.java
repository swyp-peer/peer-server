package com.example.peer.schedule.controller;

import com.example.peer.schedule.dto.request.ScheduleRuleRequest;
import com.example.peer.schedule.dto.response.PossibleSchedulesResponse;
import com.example.peer.schedule.dto.response.ScheduleRuleResponse;
import com.example.peer.schedule.service.ScheduleService;
import com.example.peer.security.service.TokenService;
import com.example.peer.security.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;

    /*
    멘토가 일정 규칙을 생성
     */
    @PostMapping("/mentor/create")
    public ResponseEntity<ScheduleRuleResponse> CreateScheduleRule(
            @RequestHeader("Authorization") String authorization,
            @RequestBody ScheduleRuleRequest scheduleRuleRequest
    ) {
        return ResponseEntity.ok()
                .body(scheduleService.CreateScheduleRule(scheduleRuleRequest, tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization))));
    }

    /*
    멘토가 일정 규칙을 수정
     */
    @PatchMapping("/mentor/update")
    public ResponseEntity<ScheduleRuleResponse> UpdateScheduleRule(
            @RequestHeader("Authorization") String authorization,
            @RequestBody ScheduleRuleRequest scheduleRuleRequest
    ) {
        return ResponseEntity.ok()
                .body(scheduleService.UpdateScheduleRule(scheduleRuleRequest, tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization))));
    }

    /*
    멘토가 자신의 일정 규칙을 조회
     */
    @GetMapping("/mentor/view")
    public ResponseEntity<ScheduleRuleResponse> ViewMyScheduleRule(
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok()
                .body(scheduleService.ViewScheduleRule(tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization))));
    }

    /*
    멘티가 멘토의 상담 가능 일정을 조회
     */
    @GetMapping("/mentee/view/{mentorId}")
    public ResponseEntity<PossibleSchedulesResponse> ViewPossibleSchedules(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("mentorId") Long mentorId
    ) {
        return ResponseEntity.ok()
                .body(scheduleService.ViewPossibleSchedules(mentorId));
    }
}
