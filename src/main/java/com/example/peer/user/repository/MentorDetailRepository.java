package com.example.peer.user.repository;

import com.example.peer.user.entity.MentorDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorDetailRepository extends JpaRepository<MentorDetail, Long> {
}
