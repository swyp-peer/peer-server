package com.example.peer.oauth2.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
class KakaoControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	@Autowired
	private UserRepository userRepository;
	private static final String base_mapping = "/oauth2/kakao";

	String getAccessToken(String username) {
		User user = User.builder()
			.name(username)
			.socialId("1")
			.profileImageUrl("/")
			.role(Role.MENTEE)
			.oauthType(OauthType.KAKAO)
			.build();
		userRepository.save(user);
		Map<String, Object> claims = user.getClaims();

		TokenInfo tokenInfo = jwtTokenProvider.generateToken(userDetailsService.loadUserById(user.getId()));
		return tokenInfo.getAccessToken();
	}

	@Test
	@DisplayName("Kakao Redirect Test")
	void KakaoRedirectTest() throws Exception {
		mockMvc.perform(get(base_mapping))
			.andExpect(status().is3xxRedirection())
			.andDo(print());
	}

	@Test
	@DisplayName("Access Token Test")
	void accessTokenTest() throws Exception {
		String accessToken = getAccessToken("username");
		mockMvc.perform(get("/nothing")
				.header("Authorization", "Bearer " + accessToken))
			.andExpect(status().isNotFound())
			.andDo(print());
	}

}