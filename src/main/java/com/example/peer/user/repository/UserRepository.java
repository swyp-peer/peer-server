package com.example.peer.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.peer.user.entity.OauthType;
import com.example.peer.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail (String email);
	Optional<User> findBySocialIdAndOauthType(String SocialId, OauthType oauthType);
}
