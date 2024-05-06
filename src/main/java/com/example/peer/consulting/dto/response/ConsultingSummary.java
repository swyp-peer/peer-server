package com.example.peer.consulting.dto.response;

import com.example.peer.consulting.entity.Consulting;
import com.example.peer.consulting.entity.State;
import com.example.peer.user.entity.MentorDetail;
import com.example.peer.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConsultingSummary {

    private Long id;

    private LocalDateTime consultingDateTime;

    private String menteeName;

    private String mentorNickname;

    private State state;

    @Builder
    public ConsultingSummary(Consulting consulting, MentorDetail mentorDetail, User mentee) {
        this.id = consulting.getId();
        this.consultingDateTime = consulting.getConsultingDateTime();
        this.menteeName = mentee.getName();
        this.mentorNickname = mentorDetail.getNickname();
        this.state = consulting.getState();
    }
}
