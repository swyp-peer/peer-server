package com.example.peer.oauth2.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.peer.oauth2.DTO.NaverTokenResponse;
import com.example.peer.oauth2.exception.OauthErrorCode;
import com.example.peer.oauth2.exception.OauthException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverLoginService {

	public String getAccessToken(HttpEntity<MultiValueMap<String, String>> request) {

		RestTemplate restTemplate = new RestTemplate();
		String accessTokenUri = "https://nid.naver.com/oauth2.0/token";
		UriComponents uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(accessTokenUri).build();

		// HTTP POST 요청
		ResponseEntity<NaverTokenResponse> response = restTemplate.exchange(uriComponentsBuilder.toUri(),
			HttpMethod.POST, request, NaverTokenResponse.class);

		if (response.getStatusCode().value() != 200) {
			throw new OauthException(OauthErrorCode.CANNOT_FETCH_ACCESS_TOKEN);
		}

		NaverTokenResponse responseBody = response.getBody();
		String accessToken = responseBody.getAccessToken();
		return accessToken;
	}

}
