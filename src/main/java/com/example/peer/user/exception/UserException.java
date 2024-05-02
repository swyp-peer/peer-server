package com.example.peer.user.exception;

import com.example.peer.common.exception.BusinessException;
import lombok.Getter;

@Getter
public class UserException extends BusinessException {

    UserErrorCode userErrorCode;

    public UserException(UserErrorCode userErrorcode) {
        super(userErrorcode);
    }
}