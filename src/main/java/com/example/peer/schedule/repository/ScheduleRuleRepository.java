package com.example.peer.schedule.repository;

import com.example.peer.schedule.entity.ScheduleRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRuleRepository extends JpaRepository<ScheduleRule, Long> {
}
