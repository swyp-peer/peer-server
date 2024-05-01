package com.example.peer.consulting.dto.response;

import com.example.peer.consulting.entity.Consulting;
import com.example.peer.consulting.entity.ConsultingDetail;
import com.example.peer.consulting.entity.State;
import com.example.peer.consulting.entity.TeamComposition;
import com.example.peer.user.entity.MentorDetail;
import com.example.peer.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConsultingDetailResponse {

    private Long id;

    private LocalDateTime consultingDateTime;

    private String message;

    private int managerCount;

    private int designerCount;

    private int frontendCount;

    private int backendCount;

    private State state;

    private String menteeName;

    private String mentorNickname;

    private String mentorPosition;

    @Builder
    public ConsultingDetailResponse(Consulting consulting, ConsultingDetail consultingDetail, TeamComposition teamComposition, MentorDetail mentorDetail, User mentee) {
        this.id = consulting.getId();
        this.consultingDateTime = consulting.getConsultingDateTime();
        this.message = consultingDetail.getMessage();
        this.managerCount = teamComposition.getManagerCount();
        this.designerCount = teamComposition.getDesignerCount();
        this.frontendCount = teamComposition.getFrontendCount();
        this.backendCount = teamComposition.getBackendCount();
        this.state = consulting.getState();
        this.menteeName = mentee.getName();
        this.mentorNickname = mentorDetail.getNickname();
        this.mentorPosition = mentorDetail.getPosition();
    }
}
