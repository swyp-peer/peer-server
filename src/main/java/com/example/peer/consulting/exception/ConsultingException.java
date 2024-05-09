package com.example.peer.consulting.exception;

import com.example.peer.common.exception.BusinessException;
import lombok.Getter;

@Getter
public class ConsultingException extends BusinessException {

    ConsultingErrorCode consultingErrorCode;

    public ConsultingException(ConsultingErrorCode consultingErrorCode) {
        super(consultingErrorCode);
    }
}
