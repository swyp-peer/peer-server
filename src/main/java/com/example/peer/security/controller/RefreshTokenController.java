package com.example.peer.security.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.peer.security.entity.TokenInfo;
import com.example.peer.security.exception.SecurityErrorCode;
import com.example.peer.security.exception.SecurityException;
import com.example.peer.security.utils.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RefreshTokenController {

	private final JwtTokenProvider jwtTokenProvider;
	private static final Logger logger = LoggerFactory.getLogger(RefreshTokenController.class);
	@PostMapping("/token/refresh")
	public ResponseEntity tokenRefresh(@RequestHeader("Authorization") String authorization, @RequestBody String refreshToken) {
		String accessToken = jwtTokenProvider.resolveToken(authorization);

		logger.info("access token = {}, refresh Token = {}", accessToken, refreshToken);

		if (refreshToken == null) {
			throw new SecurityException(SecurityErrorCode.REFRESH_TOKEN_MISSING);
		}

		Map<String, String> response = new HashMap<>();

		// Access Token 만료 여부 확인
		if (jwtTokenProvider.validateToken(accessToken).equals("Expired")) {
			if (jwtTokenProvider.validateToken(refreshToken).equals("Expired")){
				throw new SecurityException(SecurityErrorCode.REFRESH_TOKEN_EXPIRED);
			}
			else{
				TokenInfo tokenInfo = jwtTokenProvider.generateToken(SecurityContextHolder.getContext().getAuthentication());
				accessToken = tokenInfo.getAccessToken();
				refreshToken = tokenInfo.getRefreshToken();
			}
		}

		response.put("Access Token", accessToken);
		response.put("Refresh Token", refreshToken);
		return ResponseEntity.ok().body(response);
	}
}
