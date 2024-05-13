package com.example.peer.user.controller;

import com.example.peer.user.dto.request.MentorDetailRequest;
import com.example.peer.user.dto.response.MenteeDetailResponse;
import com.example.peer.user.dto.response.MentorDetailResponse;
import com.example.peer.user.dto.response.MentorForMenteeResponse;
import com.example.peer.user.dto.response.MentorSummariesResponse;
import com.example.peer.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /*
    새로운 멘토 등록
     */
    @PostMapping("/mentor/create")
    public ResponseEntity<MentorDetailResponse> CreateMentorDetail(
            MentorDetailRequest mentorDetailRequest,
            Long mentorId
    ) {
        return ResponseEntity.ok()
                .body(userService.CreateMentorDetail(mentorDetailRequest, mentorId));
    }

    /*
    멘토 상세 조회
     */
    @GetMapping("/mentor/view")
    public ResponseEntity<MentorDetailResponse> ViewMentorDetail(
            Long mentorId
    ) {
        return ResponseEntity.ok()
                .body(userService.ViewMentorDetail(mentorId));
    }

    /*
    멘토 상세 정보 수정
     */
    @PatchMapping("/mentor/update")
    public ResponseEntity<MentorDetailResponse> UpdateMentorDetail(
            MentorDetailRequest mentorDetailRequest,
            Long mentorId
    ){
        return ResponseEntity.ok()
                .body(userService.UpdateMentorDetail(mentorDetailRequest, mentorId));
    }

    /*
    멘티 상세 조회
     */
    @GetMapping("/mentee/view")
    public ResponseEntity<MenteeDetailResponse> ViewMenteeDetail(
            Long menteeId
    ) {
        return ResponseEntity.ok()
                .body(userService.ViewMenteeDetail(menteeId));
    }

    /*
    멘티가 승인받은 멘토의 리스트 조회
    */
    @GetMapping("/mentee/mentorlist")
    public ResponseEntity<MentorSummariesResponse> ViewAcceptedMentorList(
            Long menteeId
    ) {
        return ResponseEntity.ok()
                .body(userService.ViewAcceptedMentorList(menteeId));
    }

    /*
    멘티가 멘토의 정보 조회
     */
    @GetMapping("/mentee/{mentorId}")
    public ResponseEntity<MentorForMenteeResponse> ViewMentorByMentee(
            @PathVariable("mentorId") Long mentorId,
            Long menteeId
    ) {
        return ResponseEntity.ok()
                .body(userService.ViewMentorByMentee(mentorId));
    }
}
