package com.swacademy.newsletter.apiPayload.exception.handler;

import com.swacademy.newsletter.apiPayload.code.BaseErrorCode;
import com.swacademy.newsletter.apiPayload.exception.GeneralException;

public class CharactersImageHandler extends GeneralException {
    public CharactersImageHandler(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
