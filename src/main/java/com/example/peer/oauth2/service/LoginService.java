package com.example.peer.oauth2.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.peer.oauth2.entity.OAuth2UserInfo;
import com.example.peer.oauth2.exception.OauthErrorCode;
import com.example.peer.oauth2.exception.OauthException;
import com.example.peer.user.entity.OauthType;
import com.example.peer.user.entity.User;
import com.example.peer.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
	private final UserRepository userRepository;

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String googleRestAPiKey;
	@Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
	private String googleRedirectURI;
	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	private String googleClientSecret;

	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String kakaoRestAPiKey;
	@Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
	private String kakaoRedirectURI;
	@Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
	private String kakaoClientSecret;

	@Value("${spring.security.oauth2.client.registration.naver.client-id}")
	private String naverRestAPiKey;
	@Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
	private String naverRedirectURI;
	@Value("${spring.security.oauth2.client.registration.naver.client-secret}")
	private String naverClientSecret;

	private final GoogleLoginService googleLoginService;
	private final NaverLoginService naverLoginService;
	private final KakaoLoginService kakaoLoginService;

	public Optional<User> findUserByOauthUserInfo(OAuth2UserInfo oAuth2UserInfo) {
		return userRepository.findBySocialIdAndOauthType(oAuth2UserInfo.socialId(), oAuth2UserInfo.oauthType());
	}

	public OAuth2UserInfo login(String code, OauthType oauthType) {
		String accessToken = getAccessToken(code, oauthType);
		return getUserInfo(oauthType, accessToken);
	}

	public User saveMentee(OAuth2UserInfo oAuth2UserInfo) {
		User user = oAuth2UserInfo.toEntity();
		return userRepository.save(user);
	}

	public String getAccessToken(String code, OauthType oauthType) {

		String restAPiKey;
		String redirectURI;
		String clientSecret;

		switch (oauthType) {
			case KAKAO -> {
				restAPiKey = kakaoRestAPiKey;
				redirectURI = kakaoRedirectURI;
				clientSecret = kakaoClientSecret;
			}
			case NAVER -> {
				restAPiKey = naverRestAPiKey;
				redirectURI = naverRedirectURI;
				clientSecret = naverClientSecret;
			}
			case GOOGLE -> {
				restAPiKey = googleRestAPiKey;
				redirectURI = googleRedirectURI;
				clientSecret = googleClientSecret;
			}
			default -> throw new OauthException(OauthErrorCode.INVALID_OAUTH_TYPE);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", restAPiKey);
		body.add("redirect_uri", redirectURI);
		body.add("client_secret", clientSecret);
		body.add("code", code);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

		switch (oauthType) {
			case KAKAO -> {
				return kakaoLoginService.getAccessToken(request);
			}
			case NAVER -> {
				return naverLoginService.getAccessToken(request);
			}
			case GOOGLE -> {
				return googleLoginService.getAccessToken(request);
			}
			default -> throw new OauthException(OauthErrorCode.INVALID_OAUTH_TYPE);
		}
	}

	public OAuth2UserInfo getUserInfo(OauthType oauthType, String accessToken) {

		RestTemplate restTemplate = new RestTemplate();

		// Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<String> request = new HttpEntity<>(headers);

		String userInfoUri;

		switch (oauthType) {
			case KAKAO -> {
				userInfoUri = "https://kapi.kakao.com/v2/user/me";
			}
			case NAVER -> {
				userInfoUri = "https://openapi.naver.com/v1/nid/me";
			}
			case GOOGLE -> {
				userInfoUri = "https://www.googleapis.com/userinfo/v2/me";
			}
			default -> throw new OauthException(OauthErrorCode.INVALID_OAUTH_TYPE);
		}

		UriComponents uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(userInfoUri).build();

		ResponseEntity<Map<String, Object>> response = restTemplate.exchange(uriComponentsBuilder.toUri(),
			HttpMethod.GET, request, new ParameterizedTypeReference<Map<String, Object>>() {
			});
		log.debug(">> user info: {}", response.getBody().toString());
		if (response.getStatusCode().value() != 200) {
			throw new OauthException(OauthErrorCode.CANNOT_FETCH_USER_INFO);
		}

		Map<String, Object> responseBody = response.getBody();
		return OAuth2UserInfo.of(oauthType, responseBody);
		//TODO 카카오 앱 심사 이후 email도 받아오도록 처리
	}
}
