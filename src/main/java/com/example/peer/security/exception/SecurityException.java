package com.example.peer.security.exception;

import com.example.peer.common.exception.BusinessException;
import com.example.peer.oauth2.exception.OauthErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class SecurityException extends BusinessException {
	SecurityErrorCode securityErrorCode;

	public SecurityException(SecurityErrorCode securityErrorCode) {
		super(securityErrorCode);
	}
}