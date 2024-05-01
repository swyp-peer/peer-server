package com.example.peer.user.controller;

import com.example.peer.user.dto.UserDto;
import com.example.peer.user.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Key;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "test@example.com", roles = "USER")
    @DisplayName("Get User Test")
    public void testGetUserInfo() throws Exception {
        // Given
        Long userId = Long.valueOf(123);
        String email = "test@example.com";
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName("John Doe");
        userDto.setEmail(email);
        userDto.setProfileImageUrl("asdasdasd.jpg");

        String accessToken = generateJwt(email);
        // 필요한 다른 필드 설정

        when(userService.getUserInfo(email)).thenReturn(userDto);

        // When & Then
        mockMvc.perform(get("/api/users/me")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userDto.getName()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()));
    }

    private String generateJwt(String email) {
        // 보안적으로 강력한 키 생성
        Key key = Keys.hmacShaKeyFor("secretKeysecretKeysecretKeysecretKey".getBytes());

        // JWT 생성
        return Jwts.builder()
                .setSubject(email)
                .signWith(key)
                .compact();
    }
}