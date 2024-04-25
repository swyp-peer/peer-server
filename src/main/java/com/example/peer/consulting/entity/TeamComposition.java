package com.example.peer.consulting.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class TeamComposition {

    private int managerCount;

    private int designerCount;

    private int frontendCount;

    private int backendCount;

    @Builder
    public TeamComposition(int managerCount, int designerCount, int frontendCount, int backendCount) {
        this.managerCount = managerCount;
        this.designerCount = designerCount;
        this.frontendCount = frontendCount;
        this.backendCount = backendCount;
    }
}
