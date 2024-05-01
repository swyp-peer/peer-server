package com.example.peer.consulting.repository;

import com.example.peer.consulting.entity.Consulting;
import com.example.peer.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConsultingRepository extends JpaRepository<Consulting, Long> , ConsultingRepositoryCustom{
    Optional<Consulting> findByConsultingDateTimeAndMentor(LocalDateTime consultingDateTime, User mentor);
}
