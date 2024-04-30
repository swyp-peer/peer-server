package com.example.peer.consulting.repository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultingRepositoryCustom {

    public List<LocalDateTime> findConsultingDateTimeByIsAccepted(Long id);
}
