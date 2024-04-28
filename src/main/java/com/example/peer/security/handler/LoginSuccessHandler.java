package com.example.peer.security.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.peer.security.utils.JwtTokenProvider;
import com.example.peer.security.entity.TokenInfo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {


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
