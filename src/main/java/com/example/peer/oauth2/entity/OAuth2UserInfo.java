package com.example.peer.oauth2.entity;

import java.util.Map;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import com.example.peer.user.entity.OauthType;
import com.example.peer.user.entity.Role;
import com.example.peer.user.entity.User;

import lombok.Builder;

@Builder
public record OAuth2UserInfo(
	String socialId,
	String name,
	String email,
	String profile,
	OauthType oauthType
) {

	public static OAuth2UserInfo of(OauthType oauthType, Map<String, Object> attributes) throws
		OAuth2AuthenticationException {
		return switch (oauthType) { // registration id별로 userInfo 생성
			case GOOGLE -> ofGoogle(attributes);
			case KAKAO -> ofKakao(attributes);
			case NAVER -> ofNaver(attributes);
			default -> throw new OAuth2AuthenticationException("ILLEGAL_REGISTRATION_ID");
		};
	}

	private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
		return OAuth2UserInfo.builder()
			.socialId((String)attributes.get("id"))
			.name((String)attributes.get("name"))
			.email((String)attributes.get("email"))
			.profile((String)attributes.get("picture"))
			.oauthType(OauthType.GOOGLE)
			.build();
	}

	private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
		Map<String, Object> properties = (Map<String, Object>)attributes.get("properties");

		return OAuth2UserInfo.builder()
			.socialId(Long.toString((Long)attributes.get("id")))
			.name((String)properties.get("nickname"))
			//			.email((String) account.get("email"))
			.oauthType(OauthType.KAKAO)
			.profile((String)properties.get("profile_image"))
			.build();
	}

	private static OAuth2UserInfo ofNaver(Map<String, Object> attributes) {
		Map<String, Object> response = (Map<String, Object>)attributes.get("response");

		return OAuth2UserInfo.builder()
			.socialId((String)response.get("id"))
			.name((String)response.get("nickname"))
			.oauthType(OauthType.NAVER)
			.email((String)response.get("email"))
			.profile((String)response.get("profile_image"))
			.build();
	}

	public User toEntity() {
		return User.builder()
			.oauthType(oauthType)
			.socialId((socialId))
			.name(name)
			.email(email)
			.profileImageUrl(profile)
			.role(Role.MENTEE)
			.build();
	}
}