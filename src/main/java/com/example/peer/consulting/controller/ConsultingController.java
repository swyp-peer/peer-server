package com.example.peer.consulting.controller;

import com.example.peer.consulting.dto.request.ConsultingRequest;
import com.example.peer.consulting.dto.response.ConsultingDetailResponse;
import com.example.peer.consulting.dto.response.ConsultingSummariesResponse;
import com.example.peer.consulting.entity.State;
import com.example.peer.consulting.service.ConsultingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/consultings")
public class ConsultingController {

    private final ConsultingService consultingService;

    /*
    멘티가 새로운 상담을 신청
     */
    @PostMapping("/mentee/create")
    public ResponseEntity<ConsultingDetailResponse> CreateConsulting(
            ConsultingRequest consultingRequest,
            Long menteeId
    ) {
        return ResponseEntity.ok()
                .body(consultingService.CreateConsulting(consultingRequest, menteeId));
    }

    /*
    멘토가 상담 상세를 조회
     */
    @GetMapping("/mentor/{consultingId}")
    public ResponseEntity<ConsultingDetailResponse> ViewConsultingDetailMentor(
            @PathVariable("consultingId") Long consultingId,
            Long mentorId
    ) {
        return ResponseEntity.ok()
                .body(consultingService.ViewConsultingDetailMentor(consultingId, mentorId));
    }

    /*
    멘티가 상담 상세를 조회
     */
    @GetMapping("/mentee/{consultingId}")
    public ResponseEntity<ConsultingDetailResponse> ViewConsultingDetailMentee(
            @PathVariable("consultingId") Long consultingId,
            Long menteeId
    ) {
        return ResponseEntity.ok()
                .body(consultingService.ViewConsultingDetailMentee(consultingId, menteeId));
    }

    /*
    멘토가 세로운 상담을 수락
     */
    @GetMapping("/mentor/{consultingId}/accept")
    public ResponseEntity<ConsultingDetailResponse> AcceptConsulting(
            @PathVariable("consultingId") Long consultingId,
            Long mentorId
    ) {
        return ResponseEntity.ok()
                .body(consultingService.AcceptConsulting(consultingId, mentorId));
    }

    /*
    멘토가 세로운 상담을 거절
     */
    @GetMapping("/mentor/{consultingId}/reject")
    public ResponseEntity<ConsultingDetailResponse> RejectConsulting(
            @PathVariable("consultingId") Long consultingId,
            Long mentorId
    ) {
        return ResponseEntity.ok()
                .body(consultingService.RejectConsulting(consultingId, mentorId));
    }

    /*
    멘토가 자신의 지난 상담 내역들을 조회
     */
    @GetMapping("/mentor/past")
    public ResponseEntity<ConsultingSummariesResponse> ViewPastConsultingMentor(
            Long mentorId
    ) {
        return ResponseEntity.ok()
                .body(consultingService.ViewPastConsultingMentor(mentorId));
    }

    /*
    멘티가 자신의 지난 상담 내역들을 조회
     */
    @GetMapping("/mentee/past")
    public ResponseEntity<ConsultingSummariesResponse> ViewPastConsultingMentee(
            Long menteeId
    ) {
        return ResponseEntity.ok()
                .body(consultingService.ViewPastConsultingMentee(menteeId));
    }

    /*
    멘토가 자신의 수락된 진행전 상담 내역을 조회
     */
    @GetMapping("/mentor/accepted")
    public ResponseEntity<ConsultingSummariesResponse> ViewPresentAcceptedConsultingMentor(
            Long mentorId
    ) {
        return ResponseEntity.ok()
                .body(consultingService.ViewPresentConsultingMentor(mentorId, State.ACCEPTED));
    }

    /*
    멘토가 자신의 대기중인 진행전 상담 내역을 조회
     */
    @GetMapping("/mentor/waiting")
    public ResponseEntity<ConsultingSummariesResponse> ViewPresentWaitingConsultingMentor(
            Long mentorId
    ) {
        return ResponseEntity.ok()
                .body(consultingService.ViewPresentConsultingMentor(mentorId, State.WAITING));
    }

    /*
    멘토가 자신의 거절된 진행전 상담 내역을 조회
     */
    @GetMapping("/mentor/rejected")
    public ResponseEntity<ConsultingSummariesResponse> ViewPresentRejectedConsultingMentor(
            Long mentorId
    ) {
        return ResponseEntity.ok()
                .body(consultingService.ViewPresentConsultingMentor(mentorId, State.REJECTED));
    }

    /*
    멘티가 자신의 수락된 진행전 상담 내역을 조회
     */
    @GetMapping("/mentee/accepted")
    public ResponseEntity<ConsultingSummariesResponse> ViewPresentAcceptedConsultingMentee(
            Long menteeId
    ) {
        return ResponseEntity.ok()
                .body(consultingService.ViewPresentConsultingMentee(menteeId, State.ACCEPTED));
    }

    /*
    멘티가 자신의 대기중인 진행전 상담 내역을 조회
     */
    @GetMapping("/mentee/waiting")
    public ResponseEntity<ConsultingSummariesResponse> ViewPresentWaitingConsultingMentee(
            Long menteeId
    ) {
        return ResponseEntity.ok()
                .body(consultingService.ViewPresentConsultingMentee(menteeId, State.WAITING));
    }

    /*
    멘티가 자신의 거절된 진행전 상담 내역을 조회
     */
    @GetMapping("/mentee/rejected")
    public ResponseEntity<ConsultingSummariesResponse> ViewPresentRejectedConsultingMentee(
            Long menteeId
    ) {
        return ResponseEntity.ok()
                .body(consultingService.ViewPresentConsultingMentee(menteeId, State.REJECTED));
    }
}
