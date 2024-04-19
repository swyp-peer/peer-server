package com.example.peer.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.peer.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
