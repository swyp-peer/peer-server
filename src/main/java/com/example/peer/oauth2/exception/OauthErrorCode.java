package com.example.peer.oauth2.exception;

import org.springframework.http.HttpStatus;

import com.example.peer.common.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OauthErrorCode implements ErrorCode {
	CANNOT_FETCH_USER_INFO(HttpStatus.INTERNAL_SERVER_ERROR, "유저 정보를 가져올 수 없습니다."),
	CANNOT_FETCH_ACCESS_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "엑세스 토큰을 가져올 수 없습니다.");


	private final HttpStatus httpStatus;
	private final String message;

}
