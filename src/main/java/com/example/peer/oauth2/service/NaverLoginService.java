package com.example.peer.oauth2.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
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

import com.example.peer.oauth2.DTO.KakaoTokenResponse;
import com.example.peer.oauth2.DTO.KakaoUserInfoResponse;
import com.example.peer.oauth2.DTO.NaverTokenResponse;
import com.example.peer.oauth2.DTO.NaverUserInfoResponse;
import com.example.peer.oauth2.exception.OauthErrorCode;
import com.example.peer.oauth2.exception.OauthException;
import com.example.peer.user.entity.OauthType;
import com.example.peer.user.entity.Role;
import com.example.peer.user.entity.User;
import com.example.peer.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverLoginService {

	@Value("${spring.security.oauth2.client.registration.naver.client-id}")
	private String restAPiKey;

	@Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
	private String redirectURI;

	@Value("${spring.security.oauth2.client.registration.naver.client-secret}")
	private String clientSecret;

	private final UserRepository userRepository;

	// 회원가입 여부 판단
	public Optional<User> findUser(String socialId, OauthType oauthType) {
		return userRepository.findBySocialIdAndOauthType(socialId, oauthType);
	}

	// 회원가입이 되어 있지 않은 경우 랜덤으로 닉네임을 생성하고 저장
	public User saveSocialMember(String socialId, String name, String profileImageUrl, String email, OauthType oauthType) {
		User user = User.builder()
			.name(name)
			.socialId(socialId)
			.profileImageUrl(profileImageUrl)
			.oauthType(oauthType)
			.email(email)
			.role(Role.MENTEE)
			.build();
		return userRepository.save(user);
	}

	public String[] naverLogin(String code) {
		String accessToken = getAccessToken(code);
		return getUserInfo(accessToken);
	}

	public String getAccessToken(String code) {

		RestTemplate restTemplate = new RestTemplate();

		// Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

		// Body
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", restAPiKey);
		body.add("redirect_uri", redirectURI);
		body.add("client_secret", clientSecret);
		body.add("code", code);

		// Header 와 Body 를 가진 Request 생성
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

		String accessTokenUri = "https://nid.naver.com/oauth2.0/token";
		UriComponents uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(accessTokenUri).build();

		// HTTP POST 요청
		ResponseEntity<NaverTokenResponse> response = restTemplate.exchange(uriComponentsBuilder.toUri(), HttpMethod.POST, request, NaverTokenResponse.class);

		if (response.getStatusCode().value() != 200) {
			throw new OauthException(OauthErrorCode.CANNOT_FETCH_ACCESS_TOKEN);
		}

		NaverTokenResponse responseBody = response.getBody();
		String accessToken = responseBody.getAccessToken();
		return accessToken;
	}

	public String[] getUserInfo(String accessToken) {

		RestTemplate restTemplate = new RestTemplate();

		// Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<String> request = new HttpEntity<>(headers);

		String userInfoUri = "https://openapi.naver.com/v1/nid/me";
		UriComponents uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(userInfoUri).build();

		ResponseEntity<NaverUserInfoResponse> response = restTemplate.exchange(uriComponentsBuilder.toUri(), HttpMethod.GET, request, NaverUserInfoResponse.class);
		log.debug(">> user info: {}", response.getBody().toString());
		if (response.getStatusCode().value() != 200) {
			throw new OauthException(OauthErrorCode.CANNOT_FETCH_USER_INFO);
		}

		NaverUserInfoResponse responseBody = response.getBody();
		String socialId = responseBody.getResponse().getId();
		String nickname = responseBody.getResponse().getNickname();
		String picture = responseBody.getResponse().getProfile_image();
		String email = responseBody.getResponse().getEmail();

		log.info(">> socialId = {}", socialId);
		log.info(">> nickname = {}", nickname);
		log.info(">> profile image = {}", picture);
		return new String[]{socialId, nickname, picture, email};


	}



}
