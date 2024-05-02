package com.example.peer.oauth2.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.peer.oauth2.entity.OAuth2UserInfo;
import com.example.peer.oauth2.service.LoginService;
import com.example.peer.security.service.UserDetailsServiceImpl;
import com.example.peer.security.utils.JwtTokenProvider;
import com.example.peer.user.entity.OauthType;
import com.example.peer.user.entity.User;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2/kakao")
@Slf4j
public class KakaoController {
	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String restAPIKey;
	@Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
	private String redirectURI;
	private final LoginService loginService;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserDetailsServiceImpl userDetailsService;

	@GetMapping("")
	public void initiateKakaoLogin(HttpServletResponse response) throws IOException {
		String uriString = UriComponentsBuilder
			.fromUriString("https://kauth.kakao.com/oauth/authorize")
			.queryParam("response_type", "code")
			.queryParam("client_id", restAPIKey)
			.queryParam("redirect_uri", redirectURI)
			.toUriString();
		response.sendRedirect(uriString);
	}

	@GetMapping("/callback")
	public ResponseEntity kakaoLogin(@RequestParam("code") String code) {
		OAuth2UserInfo oAuth2UserInfo = loginService.login(code, OauthType.KAKAO);
		Optional<User> optionalUser = loginService.findUserByOauthUserInfo(oAuth2UserInfo);
		User user = null;
		if (optionalUser.isEmpty()) {
			user = loginService.saveMentee(oAuth2UserInfo);
		} else {
			user = optionalUser.get();
		}
		Map<String, Object> claims = user.getClaims();
		claims.put("TokenInfo", jwtTokenProvider.generateToken(userDetailsService.loadUserById(user.getId())));
		return ResponseEntity.ok().body(claims);
	}

}
