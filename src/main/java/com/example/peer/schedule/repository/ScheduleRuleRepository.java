package com.example.peer.schedule.repository;

import com.example.peer.schedule.entity.ScheduleRule;
import com.example.peer.user.entity.MentorDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRuleRepository extends JpaRepository<ScheduleRule, Long> {
    Optional<ScheduleRule> findByMentorDetail(MentorDetail mentorDetail);
}
