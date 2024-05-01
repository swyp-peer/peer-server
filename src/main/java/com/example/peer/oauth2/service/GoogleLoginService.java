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

import com.example.peer.oauth2.DTO.GoogleTokenResponse;
import com.example.peer.oauth2.DTO.GoogleUserInfoResponse;
import com.example.peer.oauth2.DTO.KakaoTokenResponse;
import com.example.peer.oauth2.DTO.KakaoUserInfoResponse;
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
public class GoogleLoginService {
	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String restAPiKey;

	@Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
	private String redirectURI;

	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	private String clientSecret;
	private final UserRepository userRepository;

	public Optional<User> findUser(String socialId, OauthType oauthType) {
		return userRepository.findBySocialIdAndOauthType(socialId, oauthType);
	}

	public String[] googleLogin(String code) {
		String accessToken = getAccessToken(code);
		return getUserInfo(accessToken);
	}

	public User saveSocialMember(String socialId, String name, String profileImageUrl, String email, OauthType oauthType) {
		User user = User.builder()
			.name(name)
			.socialId(socialId)
			.email(email)
			.profileImageUrl(profileImageUrl)
			.oauthType(oauthType)
			.role(Role.MENTEE)
			.build();
		return userRepository.save(user);
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

		String accessTokenUri = "https://oauth2.googleapis.com/token";
		UriComponents uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(accessTokenUri).build();

		// HTTP POST 요청
		ResponseEntity<GoogleTokenResponse> response = restTemplate.exchange(uriComponentsBuilder.toUri(), HttpMethod.POST, request, GoogleTokenResponse.class);

		if (response.getStatusCode().value() != 200) {
			throw new OauthException(OauthErrorCode.CANNOT_FETCH_ACCESS_TOKEN);
		}

		GoogleTokenResponse responseBody = response.getBody();
		String accessToken = responseBody.getAccessToken();
		log.debug(">> Google Access Token: {}", accessToken);
		return accessToken;
	}
	public String[] getUserInfo(String accessToken) {

		RestTemplate restTemplate = new RestTemplate();

		// Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<String> request = new HttpEntity<>(headers);

		String userInfoUri = "https://www.googleapis.com/userinfo/v2/me";
		UriComponents uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(userInfoUri).build();

		ResponseEntity<GoogleUserInfoResponse> response = restTemplate.exchange(uriComponentsBuilder.toUri(), HttpMethod.GET, request, GoogleUserInfoResponse.class);
		log.debug(">> user info: {}", response.getBody().toString());
		if (response.getStatusCode().value() != 200) {
			throw new OauthException(OauthErrorCode.CANNOT_FETCH_USER_INFO);
		}

		GoogleUserInfoResponse responseBody = response.getBody();
		String socialId = String.valueOf(responseBody.getId());
		String nickname = responseBody.getName();
		String picture = responseBody.getPicture();
		String email = responseBody.getEmail();

		log.info(">> socialId = {}", socialId);
		log.info(">> nickname = {}", nickname);
		log.info(">> profile image = {}", picture);
		log.info(">> email = {}", email);
		return new String[]{socialId, nickname, picture, email};

	}
}
