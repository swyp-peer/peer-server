package com.example.peer.security.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.peer.security.entity.TokenInfo;
import com.example.peer.security.utils.JwtTokenProvider;
import com.example.peer.user.entity.OauthType;
import com.example.peer.user.entity.Role;
import com.example.peer.user.entity.User;
import com.example.peer.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
class TokenServiceTest {
	@Autowired
	UserRepository userRepository;
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	@Autowired
	TokenService tokenService;

	TokenInfo getTokenInfo() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(Role.MENTEE.toString()));
		User user = User.builder()
			.name("name")
			.role(Role.MENTEE)
			.email("email@gmail.com")
			.socialId("aa")
			.oauthType(OauthType.NAVER)
			.build();
		userRepository.save(user);
		TokenInfo tokenInfo = jwtTokenProvider.generateToken(userDetailsService.loadUserById(user.getId()));
		return tokenInfo;
	}

	@Test
	public void getUserIdFromTokenTest() {
		TokenInfo tokenInfo = getTokenInfo();
		Assertions.assertEquals(tokenService.getUserIdFromToken(tokenInfo.getAccessToken()), Long.valueOf(1));
	}
}