package com.example.peer.oauth2.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
class GoogleControllerTest {

	@Autowired
	private MockMvc mockMvc;
	private static final String base_mappring = "/oauth2/google";
	@Test
	@DisplayName("Kakao Redirect Test")
	void GoogleRedirectTest() throws Exception {
		mockMvc.perform(get(base_mappring))
			.andExpect(status().is3xxRedirection())
			.andDo(print());
	}

}