package com.example.peer.user.controller;

import com.example.peer.user.dto.request.MentorDetailRequest;
import com.example.peer.user.dto.response.MentorDetailResponse;
import com.example.peer.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
