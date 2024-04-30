package com.example.peer.oauth2.entity;

import java.lang.reflect.Member;
import java.util.Map;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import com.example.peer.user.entity.Role;
import com.example.peer.user.entity.User;

import jakarta.security.auth.message.AuthException;
import lombok.Builder;

@Builder
public record OAuth2UserInfo(
	String name,
	String email,
	String profile
) {

	public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) throws
		OAuth2AuthenticationException {
		return switch (registrationId) { // registration id별로 userInfo 생성
			case "google" -> ofGoogle(attributes);
			case "kakao" -> ofKakao(attributes);
			case "naver" -> ofNaver(attributes);
			default -> throw new OAuth2AuthenticationException("ILLEGAL_REGISTRATION_ID");
		};
	}

	private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
		return OAuth2UserInfo.builder()
			.name((String) attributes.get("name"))
			.email((String) attributes.get("email"))
			.profile((String) attributes.get("picture"))
			.build();
	}

	private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
		Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
		Map<String, Object> profile = (Map<String, Object>) account.get("profile");

		return OAuth2UserInfo.builder()
			.name((String) profile.get("nickname"))
			.email((String) account.get("email"))
			.profile((String) profile.get("profile_image_url"))
			.build();
	}
	private static OAuth2UserInfo ofNaver(Map<String, Object> attributes) {
		Map<String, Object> response = (Map<String, Object>) attributes.get("response");

		return OAuth2UserInfo.builder()
			.name((String) response.get("name"))
			.email((String) response.get("email"))
			.profile((String) response.get("profile_image"))
			.build();
	}

	public User toEntity() {
		return User.builder()
			.name(name)
			.email(email)
			.profileImage(profile)
			.role(Role.MENTEE)
			.build();
	}
}