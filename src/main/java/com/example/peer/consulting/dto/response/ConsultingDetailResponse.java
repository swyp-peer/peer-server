package com.example.peer.consulting.dto.response;

import com.example.peer.consulting.entity.Consulting;
import com.example.peer.consulting.entity.State;
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
    public ConsultingDetailResponse(Consulting consulting) {
        this.id = consulting.getId();
        this.consultingDateTime = consulting.getConsultingDateTime();
        this.message = consulting.getConsultingDetail().getMessage();
        this.managerCount = consulting.getConsultingDetail().getTeamComposition().getManagerCount();
        this.designerCount = consulting.getConsultingDetail().getTeamComposition().getDesignerCount();
        this.frontendCount = consulting.getConsultingDetail().getTeamComposition().getFrontendCount();
        this.backendCount = consulting.getConsultingDetail().getTeamComposition().getBackendCount();
        this.state = consulting.getState();
        this.menteeName = consulting.getMentee().getName();
        this.mentorNickname = consulting.getMentor().getMentorDetail().getNickname();
        this.mentorPosition = consulting.getMentor().getMentorDetail().getPosition();
    }
}
