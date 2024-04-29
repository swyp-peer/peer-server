package com.example.peer.oauth2.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NaverUserInfoResponse {
	@JsonProperty("resultcode")
	private Long resultcode;
	@JsonProperty("message")
	private String message;

	@JsonProperty("response")
	private Response response;

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Response {
		private String id;
		private String nickname;
		private String profile_image;
		private String email;
	}
}