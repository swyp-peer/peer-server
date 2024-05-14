package com.example.peer.user.controller;

import com.example.peer.security.service.TokenService;
import com.example.peer.security.utils.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;

    /*
    새로운 멘토 등록
     */
    @PostMapping("/mentor/create")
    public ResponseEntity<MentorDetailResponse> CreateMentorDetail(
            @RequestHeader("Authorization") String authorization,
            @RequestBody MentorDetailRequest mentorDetailRequest
    ) {
        return ResponseEntity.ok()
                .body(userService.CreateMentorDetail(mentorDetailRequest, tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization))));
    }

    /*
    멘토 상세 조회
     */
    @GetMapping("/mentor/view")
    public ResponseEntity<MentorDetailResponse> ViewMentorDetail(
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok()
                .body(userService.ViewMentorDetail(tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization))));
    }

    /*
    멘토 상세 정보 수정
     */
    @PatchMapping("/mentor/update")
    public ResponseEntity<MentorDetailResponse> UpdateMentorDetail(
            @RequestHeader("Authorization") String authorization,
            @RequestBody MentorDetailRequest mentorDetailRequest
    ){
        return ResponseEntity.ok()
                .body(userService.UpdateMentorDetail(mentorDetailRequest, tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization))));
    }

    /*
    멘티 상세 조회
     */
    @GetMapping("/mentee/view")
    public ResponseEntity<MenteeDetailResponse> ViewMenteeDetail(
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok()
                .body(userService.ViewMenteeDetail(tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization))));
    }

    /*
    멘티가 승인받은 멘토의 리스트 조회
    */
    @GetMapping("/mentee/mentorlist")
    public ResponseEntity<MentorSummariesResponse> ViewAcceptedMentorList(
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok()
                .body(userService.ViewAcceptedMentorList(tokenService.getUserIdFromToken(jwtTokenProvider.resolveToken(authorization))));
    }

    /*
    멘티가 멘토의 정보 조회
     */
    @GetMapping("/mentee/{mentorId}")
    public ResponseEntity<MentorForMenteeResponse> ViewMentorByMentee(
            @PathVariable("mentorId") Long mentorId,
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok()
                .body(userService.ViewMentorByMentee(mentorId));
    }
}
