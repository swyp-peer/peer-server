package com.example.peer.user.dto.response;

import com.example.peer.user.entity.Keyword;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MentorSummary {

    private String nickname;

    private String position;

    private List<Keyword> keywords;

    @Builder
    public MentorSummary(String nickname, String position, List<Keyword> keywords) {
        this.nickname = nickname;
        this.position = position;
        this.keywords = keywords;
    }
}
