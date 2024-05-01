package com.example.peer.oauth2.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.peer.oauth2.DTO.KakaoTokenResponse;
import com.example.peer.oauth2.exception.OauthErrorCode;
import com.example.peer.oauth2.exception.OauthException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLoginService {

	public String getAccessToken(HttpEntity<MultiValueMap<String, String>> request) {

		RestTemplate restTemplate = new RestTemplate();

		String accessTokenUri = "https://kauth.kakao.com/oauth/token";
		UriComponents uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(accessTokenUri).build();
		ResponseEntity<KakaoTokenResponse> response = restTemplate.exchange(uriComponentsBuilder.toUri(),
			HttpMethod.POST, request, KakaoTokenResponse.class);

		if (response.getStatusCode().value() != 200) {
			throw new OauthException(OauthErrorCode.CANNOT_FETCH_ACCESS_TOKEN);
		}

		KakaoTokenResponse responseBody = response.getBody();
		String accessToken = responseBody.getAccessToken();
		return accessToken;
	}
}
