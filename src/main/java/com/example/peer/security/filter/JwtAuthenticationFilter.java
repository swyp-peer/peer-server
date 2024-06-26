package com.example.peer.security.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.peer.security.handler.AuthenticationExceptionHandler;
import com.example.peer.security.utils.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final ObjectMapper objectMapper;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		ServletException,
		IOException {
		// Get jwt token and validate
		log.debug(">>JWTAuthenticationFilter DoFilterInternal Activated");
		String token = resolveToken(request);
		if (token != null && jwtTokenProvider.validateToken(token).equals("Valid")) {
			Authentication authentication = jwtTokenProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);

		} else {
			response.setContentType("application/json"); // JSON으로 사용자에게 전달
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Http Status 401로 전달
			response.setCharacterEncoding("utf-8"); // 본문 내용은 UTF-8
			response.getWriter()
				.write(objectMapper.writeValueAsString(
					new AuthenticationExceptionHandler.LoginFailResponse("유효하지 않은 요청입니다."))); //
			return;
		}
		chain.doFilter(request, response);
		// refresh token 검증하는 부분 필요

	}

	// Get authorization header and get Token Information
	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			log.debug(">> resolved token by JwtAuthenticationFilter");
			return bearerToken.substring(7);
		}

		return null;
	}
}