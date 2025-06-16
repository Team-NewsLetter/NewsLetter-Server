package com.swacademy.newsletter.apiPayload.exception.handler;

import com.swacademy.newsletter.apiPayload.code.BaseErrorCode;
import com.swacademy.newsletter.apiPayload.exception.GeneralException;

public class CharacterHandler extends GeneralException {
    public CharacterHandler(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
