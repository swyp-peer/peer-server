package com.example.peer.security.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import com.example.peer.security.entity.TokenInfo;
import com.example.peer.security.service.UserDetailsServiceImpl;
import com.example.peer.security.utils.JwtTokenProvider;
import com.example.peer.user.entity.OauthType;
import com.example.peer.user.entity.Role;
import com.example.peer.user.entity.User;
import com.example.peer.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
class RefreshTokenControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	private static final String base_mapping = "/token/refresh";

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
	@DisplayName("Refresh Token Test")
	void RefreshTokenTest() throws Exception {
		TokenInfo tokenInfo = getTokenInfo();
		mockMvc.perform(post(base_mapping)
				.header("Authorization", "Bearer " + tokenInfo.getAccessToken())
				.content(tokenInfo.getRefreshToken()))
			.andExpect(status().isOk())
			.andDo(print());
	}
}