package com.example.peer.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.peer.user.entity.OauthType;
import com.example.peer.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
	Optional<User> findByEmail (String email);
	Optional<User> findBySocialIdAndOauthType(String SocialId, OauthType oauthType);
	Optional<User> findById(Long id);
	Optional<User> findByName(String Name);
}
