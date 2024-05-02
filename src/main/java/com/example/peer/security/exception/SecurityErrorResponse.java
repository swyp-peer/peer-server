package com.example.peer.security.exception;

import org.springframework.http.ResponseEntity;

import com.example.peer.oauth2.exception.OauthErrorCode;
import com.example.peer.user.entity.OauthType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SecurityErrorResponse {
	private int status;
	private String code;
	private String message;

	public static ResponseEntity<SecurityErrorResponse> toResponseEntity(SecurityErrorCode e) {
		return ResponseEntity
			.status(e.getHttpStatus())
			.body(SecurityErrorResponse.builder()
				.status(e.getHttpStatus().value())
				.code(e.name())
				.message(e.getMessage())
				.build()
			);
	}
}