package com.example.peer.user.dto.response;

import com.example.peer.user.entity.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MentorDetailResponse {

    private Long mentorId;

    private String name;

    private String email;

    private String phoneNumber;

    private String nickname;

    private String position;

    private String introduction;

    private Boolean isAccepted;

    private String openTalkLink;

    private List<Keyword> keywords;

    @Builder
    public MentorDetailResponse(User mentor, MentorDetail mentorDetail, List<KeywordMentorDetail> keywordMentorDetails) {
        this.mentorId = mentor.getId();
        this.name = mentor.getName();
        this.email = mentor.getEmail();
        this.phoneNumber = mentor.getPhoneNumber();
        this.nickname = mentorDetail.getNickname();
        this.position = mentorDetail.getPosition();
        this.introduction = mentorDetail.getIntroduction();
        this.isAccepted = mentorDetail.getIsAccepted();
        this.openTalkLink = mentorDetail.getOpenTalkLink();
        this.keywords = new ArrayList<>();
        for(KeywordMentorDetail keywordMentorDetail : keywordMentorDetails){
            this.keywords.add(keywordMentorDetail.getKeyword());
        }
    }
}
