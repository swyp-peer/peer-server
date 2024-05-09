package com.example.peer.security.exception;

import org.springframework.http.HttpStatus;

import com.example.peer.common.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SecurityErrorCode implements ErrorCode {
	REFRESH_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "리프레쉬 토큰이 만료되었습니다."),
	USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "유저를 찾을 수 없습니다."),
	REFRESH_TOKEN_MISSING(HttpStatus.BAD_REQUEST, "리프레쉬 토큰이 없습니다.");

	private final HttpStatus httpStatus;
	private final String message;

}