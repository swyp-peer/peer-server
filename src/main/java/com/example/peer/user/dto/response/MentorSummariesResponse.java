package com.example.peer.user.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MentorSummariesResponse {

    private List<MentorSummary> mentorSummaries;

    @Builder
    public MentorSummariesResponse() {
        this.mentorSummaries = new ArrayList<>();
    }

    public void UpdateMentorSummary(MentorSummary mentorSummary) {
        this.mentorSummaries.add(mentorSummary);
    }
}
