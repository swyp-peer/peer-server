package com.example.peer.user.dto.response;

import com.example.peer.user.entity.Keyword;
import com.example.peer.user.entity.MentorDetail;
import com.example.peer.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MentorSummary {

    private Long mentorId;

    private String nickname;

    private String position;

    private List<Keyword> keywords;

    @Builder
    public MentorSummary(User mentor, MentorDetail mentorDetail) {
        this.mentorId = mentor.getId();
        this.nickname = mentorDetail.getNickname();
        this.position = mentorDetail.getPosition();
        this.keywords = mentorDetail.getKeywords();
    }
}
