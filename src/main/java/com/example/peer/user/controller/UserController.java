package com.example.peer.user.controller;

import com.example.peer.user.dto.UserDto;
import com.example.peer.user.dto.UpdateUserRequest;
import com.example.peer.user.service.UserService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value="me",
        produces=MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> getUserInfo(@RequestHeader("Authorization") String accessToken) {
        // Access token에서 email 추출 (JWT 사용 가정)
        String email = extractEmailFromToken(accessToken);
        UserDto userDto = userService.getUserInfo(email);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(userDto);
            return ResponseEntity.ok().body(json);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request");
        }
    }

    @PostMapping("me")
    public ResponseEntity<UserDto> updateUserInfo(@RequestHeader("Authorization") String accessToken,
                                                  @RequestBody UpdateUserRequest request) {
        String email = extractEmailFromToken(accessToken);
        UserDto updatedUserDto = userService.updateUserInfo(email, request);
        return ResponseEntity.ok(updatedUserDto);
    }

    private String extractEmailFromToken(String accessToken) {
        try {
            Key key = Keys.hmacShaKeyFor("secretKeysecretKeysecretKeysecretKey".getBytes());

            Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(accessToken)
                .getBody();
            
            String email = claims.getSubject();

            return email;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid access token");
        }
    }
}