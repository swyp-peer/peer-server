package com.example.peer.consulting.controller;

import com.example.peer.consulting.dto.request.ConsultingRequest;
import com.example.peer.consulting.dto.response.ConsultingDetailResponse;
import com.example.peer.consulting.dto.response.ConsultingSummariesResponse;
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
    @PostMapping("/create")
    public ResponseEntity<ConsultingDetailResponse> CreateConsulting(
            ConsultingRequest consultingRequest,
            Long menteeId
    ) {
        return ResponseEntity.ok()
                .body(consultingService.CreateConsulting(consultingRequest, menteeId));
    }

    /*
    멘토가 세로운 상담을 수락
     */
    @GetMapping("/{consultingId}/accept")
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
    @GetMapping("/{consultingId}/reject")
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
    
     */
}
