package com.example.peer.security.service;

import org.springframework.stereotype.Service;

import com.example.peer.security.utils.JwtTokenProvider;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {
	private final JwtTokenProvider jwtTokenProvider;

	public Long getUserIdFromToken(String accessToken) {
		Claims claims = jwtTokenProvider.parseClaims(accessToken);
		return Long.valueOf(claims.getSubject());
	}
}
