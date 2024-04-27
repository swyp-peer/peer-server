package com.example.peer.notification.entity;

import com.example.peer.common.domain.BaseTimeEntity;
import com.example.peer.consulting.entity.Consulting;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    private String message;

    private LocalDateTime reservedDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_id")
    private Consulting consulting;

    @Builder
    public Notification(String message, LocalDateTime reservedDate, Consulting consulting) {
        this.message = message;
        this.reservedDate = reservedDate;
        this.consulting = consulting;
    }
}
