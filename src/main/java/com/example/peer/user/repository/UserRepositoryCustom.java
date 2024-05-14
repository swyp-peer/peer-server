package com.example.peer.user.repository;

import com.example.peer.user.entity.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findMentorByIsAccepted();
}
