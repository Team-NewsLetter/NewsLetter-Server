package com.swacademy.newsletter.apiPayload.exception.handler;

import com.swacademy.newsletter.apiPayload.code.BaseErrorCode;
import com.swacademy.newsletter.apiPayload.exception.GeneralException;

public class CertificationHandler extends GeneralException {
    public CertificationHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
