package com.example.peer.oauth2.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.peer.oauth2.JwtTokenProvider;
import com.example.peer.oauth2.entity.TokenInfo;
import com.example.peer.oauth2.service.OAuth2UserService;
import com.nimbusds.oauth2.sdk.token.TokenEncoding;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final OAuth2UserService oAuth2UserService;


	private final JwtTokenProvider jwtTokenProvider;
	private static final String URI = "/oauth2/success";

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		// accessToken, refreshToken 발급
		TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

		// 토큰 전달을 위한 redirect
		String redirectUrl = UriComponentsBuilder.fromUriString(URI)
			.queryParam("Bearer", tokenInfo)
			.build().toUriString();

		response.sendRedirect(redirectUrl);
	}
}
