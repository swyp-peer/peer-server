package com.example.peer.consulting.entity;

import com.example.peer.common.domain.BaseTimeEntity;
import com.example.peer.schedule.entity.Schedule;
import com.example.peer.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Consulting extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consulting_id")
    private Long id;

    private Boolean isAccepted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "user_id", name = "mentor_id")
    private User mentor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "user_id", name = "mentee_id")
    private User mentee;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_detail_id")
    private ConsultingDetail consultingDetail;

    @Builder
    public Consulting(User mentor, User mentee, Schedule schedule, ConsultingDetail consultingDetail) {
        this.mentor = mentor;
        this.mentee = mentee;
        this.schedule = schedule;
        this.consultingDetail = consultingDetail;
        this.isAccepted = Boolean.FALSE;
    }
}
