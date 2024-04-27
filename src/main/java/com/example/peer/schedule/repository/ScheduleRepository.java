package com.example.peer.schedule.repository;

import com.example.peer.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    boolean existsByPossibleSchedule(LocalDateTime possibleSchedule);
}
