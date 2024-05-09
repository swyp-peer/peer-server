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
@Slf4j
@RequestMapping("/oauth2/google")
public class GoogleController {
	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String restAPIKey;
	@Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
	private String redirectURI;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserDetailsServiceImpl userDetailsService;
	private final LoginService loginService;

	@GetMapping("")
	public void initiateGoogleLogin(HttpServletResponse response) throws IOException {
		String uriString = UriComponentsBuilder
			.fromUriString("https://accounts.google.com/o/oauth2/v2/auth")
			.queryParam("response_type", "code")
			.queryParam("client_id", restAPIKey)
			.queryParam("redirect_uri", redirectURI)
			.queryParam("scope", "profile email")
			.toUriString();
		response.sendRedirect(uriString);
	}

	@GetMapping("/callback")
	public void googleLogin(HttpServletResponse response, @RequestParam("code") String code) throws IOException {
		OAuth2UserInfo oAuth2UserInfo = loginService.login(code, OauthType.GOOGLE);
		Optional<User> optionalUser = loginService.findUserByOauthUserInfo(oAuth2UserInfo);
		User user = null;
		if (optionalUser.isEmpty()) {
			log.debug(">> optional user was empty");
			user = loginService.saveMentee(oAuth2UserInfo);
		} else {
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
