package com.example.peer.oauth2.exception;

import com.example.peer.common.exception.BusinessException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class OauthException extends BusinessException {
	OauthErrorCode oauthErrorCode;

	public OauthException(OauthErrorCode oauthErrorCode) {
		super(oauthErrorCode);
	}
}