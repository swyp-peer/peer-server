package com.example.peer.oauth2.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.peer.oauth2.entity.OAuth2UserInfo;
import com.example.peer.oauth2.service.LoginService;
import com.example.peer.security.entity.TokenInfo;
import com.example.peer.security.service.UserDetailsServiceImpl;
import com.example.peer.security.utils.JwtTokenProvider;
import com.example.peer.user.entity.OauthType;
import com.example.peer.user.entity.User;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2/naver")
@Slf4j
public class NaverController {
	@Value("${spring.security.oauth2.client.registration.naver.client-id}")
	private String restAPIKey;
	@Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
	private String redirectURI;
	private final LoginService loginService;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserDetailsServiceImpl userDetailsService;

	@GetMapping("")
	public void initiateNaverLogin(HttpServletResponse response) throws IOException {
		String uriString = UriComponentsBuilder
			.fromUriString("https://nid.naver.com/oauth2.0/authorize")
			.queryParam("response_type", "code")
			.queryParam("client_id", restAPIKey)
			.queryParam("redirect_uri", redirectURI)
			.toUriString();
		response.sendRedirect(uriString);
	}

	@GetMapping("/callback")
	public void naverLogin(HttpServletResponse response, @RequestParam("code") String code) throws
		IOException {
		OAuth2UserInfo oAuth2UserInfo = loginService.login(code, OauthType.NAVER);
		Optional<User> optionalUser = loginService.findUserByOauthUserInfo(oAuth2UserInfo);
		User user = null;
		if (optionalUser.isEmpty()) {
			log.debug(">> optional user was empty");
			user = loginService.saveMentee(oAuth2UserInfo);
		} else {
			log.debug(">> optional user was not empty");
			user = optionalUser.get();
		}
		TokenInfo tokenInfo = jwtTokenProvider.generateToken(userDetailsService.loadUserById(user.getId()));
		String uriString = UriComponentsBuilder
			.fromUriString("https://localhost:3000/oauth/callback")
			.toUriString();
		response.addHeader("AccessToken", tokenInfo.getAccessToken());
		response.addHeader("RefreshToken", tokenInfo.getRefreshToken());
		response.sendRedirect(uriString);
	}
}
