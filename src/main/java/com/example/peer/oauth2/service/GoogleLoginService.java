package com.example.peer.oauth2.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.peer.oauth2.DTO.GoogleTokenResponse;
import com.example.peer.oauth2.exception.OauthErrorCode;
import com.example.peer.oauth2.exception.OauthException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleLoginService {

	public String getAccessToken(HttpEntity<MultiValueMap<String, String>> request) {

		RestTemplate restTemplate = new RestTemplate();

		UriComponents uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com/token")
			.build();
		ResponseEntity<GoogleTokenResponse> response = restTemplate.exchange(uriComponentsBuilder.toUri(),
			HttpMethod.POST, request, GoogleTokenResponse.class);

		if (response.getStatusCode().value() != 200) {
			throw new OauthException(OauthErrorCode.CANNOT_FETCH_ACCESS_TOKEN);
		}

		GoogleTokenResponse responseBody = response.getBody();
		String accessToken = responseBody.getAccessToken();
		log.debug(">> Google Access Token: {}", accessToken);
		return accessToken;
	}
}
