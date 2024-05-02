package com.example.peer.schedule.exception;

import com.example.peer.common.exception.BusinessException;
import lombok.Getter;

@Getter
public class ScheduleException extends BusinessException {

    ScheduleErrorCode scheduleErrorCode;

    public ScheduleException(ScheduleErrorCode scheduleErrorCode) {
        super(scheduleErrorCode);
    }
}
