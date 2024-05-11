package com.example.peer.user.dto.request;

import com.example.peer.user.entity.Keyword;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MentorDetailRequest {

    private String nickname;

    private String position;

    private String introduction;

    private Boolean isAccepted;

    private String openTalkLink;

    private List<Keyword> keywords;

    @Builder
    public MentorDetailRequest(String nickname, String position, String introduction, Boolean isAccepted, String openTalkLink, List<Keyword> keywords) {
        this.nickname = nickname;
        this.position = position;
        this.introduction = introduction;
        this.isAccepted = isAccepted;
        this.openTalkLink = openTalkLink;
        this.keywords = keywords;
    }
}
