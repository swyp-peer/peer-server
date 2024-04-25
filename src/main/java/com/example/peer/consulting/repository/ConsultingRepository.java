package com.example.peer.consulting.repository;

import com.example.peer.consulting.entity.Consulting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultingRepository extends JpaRepository<Consulting, Long> {
}
