package com.example.peer.consulting.controller;

import com.example.peer.consulting.dto.request.ConsultingRequest;
import com.example.peer.consulting.dto.response.ConsultingDetailResponse;
import com.example.peer.consulting.dto.response.ConsultingSummariesResponse;
import com.example.peer.consulting.entity.State;
import com.example.peer.consulting.service.ConsultingService;
import com.example.peer.security.service.TokenService;
import com.example.peer.security.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/consultings")
public class ConsultingController {

    private final ConsultingService consultingService;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;

    /*
    멘티가 새로운 상담을 신청
     */
    @PostMapping("/mentee/create")
    public ResponseEntity<ConsultingDetailResponse> CreateConsulting(
            @RequestHeader("Authorization") String authorization,
            @RequestBody ConsultingRequest consultingRequest
    ) {
        return ResponseEntity.ok()
                .body(consultingService.CreateConsulting(consultingRequest, tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization))));
    }

    /*
    멘토가 상담 상세를 조회
     */
    @GetMapping("/mentor/{consultingId}")
    public ResponseEntity<ConsultingDetailResponse> ViewConsultingDetailMentor(
            @PathVariable("consultingId") Long consultingId,
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok()
                .body(consultingService.ViewConsultingDetailMentor(consultingId, tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization))));
    }

    /*
    멘티가 상담 상세를 조회
     */
    @GetMapping("/mentee/{consultingId}")
    public ResponseEntity<ConsultingDetailResponse> ViewConsultingDetailMentee(
            @PathVariable("consultingId") Long consultingId,
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok()
                .body(consultingService.ViewConsultingDetailMentee(consultingId, tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization))));
    }

    /*
    멘토가 세로운 상담을 수락
     */
    @GetMapping("/mentor/{consultingId}/accept")
    public ResponseEntity<ConsultingDetailResponse> AcceptConsulting(
            @PathVariable("consultingId") Long consultingId,
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok()
                .body(consultingService.AcceptConsulting(consultingId, tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization))));
    }

    /*
    멘토가 세로운 상담을 거절
     */
    @GetMapping("/mentor/{consultingId}/reject")
    public ResponseEntity<ConsultingDetailResponse> RejectConsulting(
            @PathVariable("consultingId") Long consultingId,
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok()
                .body(consultingService.RejectConsulting(consultingId, tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization))));
    }

    /*
    멘토가 자신의 과거 상담 리스트 조회
     */
    @GetMapping("/mentor/past")
    public ResponseEntity<ConsultingSummariesResponse> ViewPastConsultingMentor(
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok()
                .body(consultingService.ViewPastConsultingMentor(tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization))));
    }

    /*
    멘티가 자신의 과거 상담 리스트 조회
     */
    @GetMapping("/mentee/past")
    public ResponseEntity<ConsultingSummariesResponse> ViewPastConsultingMentee(
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok()
                .body(consultingService.ViewPastConsultingMentee(tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization))));
    }

    /*
    멘토가 자신의 수락된 진행 예정 상담 리스트 조회
     */
    @GetMapping("/mentor/accepted")
    public ResponseEntity<ConsultingSummariesResponse> ViewPresentAcceptedConsultingMentor(
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok()
                .body(consultingService.ViewPresentConsultingMentor(tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization)), State.ACCEPTED));
    }

    /*
    멘토가 자신의 대기중 진행 예정 상담 리스트 조회
     */
    @GetMapping("/mentor/waiting")
    public ResponseEntity<ConsultingSummariesResponse> ViewPresentWaitingConsultingMentor(
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok()
                .body(consultingService.ViewPresentConsultingMentor(tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization)), State.WAITING));
    }

    /*
    멘토가 자신의 거절된 진행 예정 상담 리스트 조회
     */
    @GetMapping("/mentor/rejected")
    public ResponseEntity<ConsultingSummariesResponse> ViewPresentRejectedConsultingMentor(
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok()
                .body(consultingService.ViewPresentConsultingMentor(tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization)), State.REJECTED));
    }

    /*
    멘티가 자신의 수락된 진행 예정 상담 리스트 조회
     */
    @GetMapping("/mentee/accepted")
    public ResponseEntity<ConsultingSummariesResponse> ViewPresentAcceptedConsultingMentee(
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok()
                .body(consultingService.ViewPresentConsultingMentee(tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization)), State.ACCEPTED));
    }

    /*
    멘티가 자신의 대기중 진행 예정 상담 리스트 조회
     */
    @GetMapping("/mentee/waiting")
    public ResponseEntity<ConsultingSummariesResponse> ViewPresentWaitingConsultingMentee(
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok()
                .body(consultingService.ViewPresentConsultingMentee(tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization)), State.WAITING));
    }

    /*
    멘티가 자신의 거절된 진행 예정 상담 리스트 조회
     */
    @GetMapping("/mentee/rejected")
    public ResponseEntity<ConsultingSummariesResponse> ViewPresentRejectedConsultingMentee(
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok()
                .body(consultingService.ViewPresentConsultingMentee(tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization)), State.REJECTED));
    }
}
