package com.example.peer.consulting.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConsultingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consulting_detail_id")
    private Long id;

    private String message;

    @Embedded
    private TeamComposition teamComposition;

    @Builder
    public ConsultingDetail(String message, TeamComposition teamComposition) {
        this.message = message;
        this.teamComposition = teamComposition;
    }
}
