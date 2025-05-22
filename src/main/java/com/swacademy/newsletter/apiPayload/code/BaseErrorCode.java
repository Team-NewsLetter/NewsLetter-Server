package com.swacademy.newsletter.apiPayload.code;


public interface BaseErrorCode {
    ErrorReasonDto getReason();
    ErrorReasonDto getReasonHttpStatus();
}