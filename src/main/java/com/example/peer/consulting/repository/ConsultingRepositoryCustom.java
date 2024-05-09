package com.example.peer.consulting.repository;

import com.example.peer.consulting.entity.Consulting;
import com.example.peer.consulting.entity.State;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultingRepositoryCustom {

    List<LocalDateTime> findConsultingDateTimeByIsAccepted(Long id);
    List<Consulting> findPastConsultingsByMentorId(Long id);
    List<Consulting> findPastConsultingsByMenteeId(Long id);
    List<Consulting> findPresentConsultingsByMentorIdAndState(Long id, State state);
    List<Consulting> findPresentConsultingsByMenteeIdAndState(Long id, State state);
}
