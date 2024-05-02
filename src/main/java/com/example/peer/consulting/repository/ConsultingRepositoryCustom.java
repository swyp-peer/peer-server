package com.example.peer.consulting.repository;

import com.example.peer.consulting.entity.Consulting;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultingRepositoryCustom {

    List<LocalDateTime> findConsultingDateTimeByIsAccepted(Long id);
    List<Consulting> findPastConsultingsByMentorId(Long id);
}
