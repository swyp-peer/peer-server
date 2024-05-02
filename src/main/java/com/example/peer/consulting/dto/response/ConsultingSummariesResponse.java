package com.example.peer.consulting.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ConsultingSummariesResponse {

    private List<ConsultingSummary> consultingSummaries;

    @Builder
    public ConsultingSummariesResponse() {
        this.consultingSummaries = new ArrayList<>();
    }

    public void UpdateConsultingSummary(ConsultingSummary consultingSummary) {
        this.consultingSummaries.add(consultingSummary);
    }
}
