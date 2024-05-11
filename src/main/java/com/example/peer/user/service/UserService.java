package com.example.peer.user.service;

import com.example.peer.user.dto.request.MentorDetailRequest;
import com.example.peer.user.dto.response.MentorDetailResponse;
import com.example.peer.user.entity.Keyword;
import com.example.peer.user.entity.KeywordMentorDetail;
import com.example.peer.user.entity.MentorDetail;
import com.example.peer.user.entity.User;
import com.example.peer.user.exception.UserErrorCode;
import com.example.peer.user.exception.UserException;
import com.example.peer.user.repository.KeywordMentorDetailRepository;
import com.example.peer.user.repository.MentorDetailRepository;
import com.example.peer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final MentorDetailRepository mentorDetailRepository;
    private final KeywordMentorDetailRepository keywordMentorDetailRepository;

    /*
    멘토-새로운 멘토 등록
    oauth를 통해 user에는 이미 저장 가정
     */
    @Transactional
    public MentorDetailResponse CreateMentorDetail(MentorDetailRequest mentorDetailRequest, Long mentorId) {
        MentorDetail mentorDetail = mentorDetailRepository.save(MentorDetail.builder()
                .nickname(mentorDetailRequest.getNickname())
                .position(mentorDetailRequest.getPosition())
                .introduction(mentorDetailRequest.getIntroduction())
                .openTalkLink(mentorDetailRequest.getOpenTalkLink())
                .build());
        List<KeywordMentorDetail> keywordMentorDetails = new ArrayList<>();
        for(Keyword keyword : mentorDetailRequest.getKeywords()){
            keywordMentorDetails.add(keywordMentorDetailRepository.save(KeywordMentorDetail.builder()
                    .mentorDetail(mentorDetail)
                    .keyword(keyword)
                    .build()));

        }
        User mentor = userRepository.findById(mentorId).orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );
        mentor.UpdateMentorDetail(mentorDetail);
        return MentorDetailResponse.builder()
                .mentorDetail(mentorDetail)
                .mentor(mentor)
                .keywordMentorDetails(keywordMentorDetails)
                .build();
    }

    /*
    멘토-승인과정 -> 추후에 관리자 페이지 도입시 추가
     */

    /*
    멘토-멘토 상세 조회
     */
    public MentorDetailResponse ViewMentorDetail(Long mentorId) {
        User mentor = userRepository.findById(mentorId).orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );
        return MentorDetailResponse.builder()
                .mentorDetail(mentor.getMentorDetail())
                .mentor(mentor)
                .keywordMentorDetails(mentor.getMentorDetail().getKeywordMentorDetails())
                .build();
    }

}
