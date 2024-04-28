package com.example.peer.oauth2.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoUserInfoResponse {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("properties")
	private KakaoProperties properties;

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class KakaoProperties {
		private String nickname;
		private String profile_image;
	}
}