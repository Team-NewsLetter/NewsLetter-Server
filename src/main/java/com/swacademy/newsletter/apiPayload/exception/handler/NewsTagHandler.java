package com.swacademy.newsletter.apiPayload.exception.handler;

import com.swacademy.newsletter.apiPayload.code.BaseErrorCode;
import com.swacademy.newsletter.apiPayload.exception.GeneralException;

public class NewsTagHandler extends GeneralException {
    public NewsTagHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
