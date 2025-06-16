package com.swacademy.newsletter.apiPayload.exception.handler;

import com.swacademy.newsletter.apiPayload.code.BaseErrorCode;
import com.swacademy.newsletter.apiPayload.exception.GeneralException;

public class UserHandler extends GeneralException {
    public UserHandler(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
