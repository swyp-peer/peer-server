package com.example.peer.consulting.entity;

import com.example.peer.common.domain.BaseTimeEntity;
import com.example.peer.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Consulting extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consulting_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "user_id", name = "mentor_id")
    private User mentor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "user_id", name = "mentee_id")
    private User mentee;

    private LocalDateTime consultingDateTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_detail_id")
    private ConsultingDetail consultingDetail;

    @Builder
    public Consulting(User mentor, User mentee, LocalDateTime consultingDateTime, ConsultingDetail consultingDetail) {
        this.mentor = mentor;
        this.mentee = mentee;
        this.consultingDateTime = consultingDateTime;
        this.consultingDetail = consultingDetail;
        this.state = State.WAITING;
    }

    public void UpdateState(State state) {
        this.state = state;
    }
}
