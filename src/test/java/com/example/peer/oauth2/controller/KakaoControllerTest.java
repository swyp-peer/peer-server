package com.example.peer.oauth2.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import lombok.RequiredArgsConstructor;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
class KakaoControllerTest {
	@Autowired
	private MockMvc mockMvc;
	private static final String base_mappring = "/oauth2/kakao";
	@Test
	@DisplayName("Kakao Redirect Test")
	void KakaoRedirectTest() throws Exception {
		mockMvc.perform(get(base_mappring))
			.andExpect(status().isOk())
			.andDo(print());
	}

}