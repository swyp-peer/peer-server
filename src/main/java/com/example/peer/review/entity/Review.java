package com.example.peer.review.entity;

import com.example.peer.common.domain.BaseTimeEntity;
import com.example.peer.consulting.entity.Consulting;
import com.example.peer.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private int satisfaction;

    private String message;

    private int charge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "user_id", name = "mentee_id")
    private User mentee;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_id")
    private Consulting consulting;

    @Builder
    public Review(int satisfaction, String message, User mentee, Consulting consulting) {
        this.satisfaction = satisfaction;
        this.message = message;
        this.mentee = mentee;
        this.consulting = consulting;
        this.charge = 0;
    }

    public void UpdateCharge(int charge) {
        this.charge = charge;
    }
}
