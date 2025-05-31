package com.swacademy.newsletter.apiPayload.code.status;

import com.swacademy.newsletter.apiPayload.code.BaseErrorCode;
import com.swacademy.newsletter.apiPayload.code.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 뉴스 관련 에러
    CARD_NEWS_NOT_FOUNT(HttpStatus.NOT_FOUND, "NEWS400", "해당 뉴스는 존재하지 않습니다."),
    NEWS_TAG_NOT_FOUND(HttpStatus.NOT_FOUND, "NEWSTAG400", "해당 뉴스 태그는 존재하지 않습니다."),
    INVALID_NEWS_TYPE (HttpStatus.BAD_REQUEST, "NEWS401", "뉴스 타입에 해당하는 유저 반응이 아닙니다."),

    // 유저 회원가입 관련 에러
    PASSWORD_MISMATCH(HttpStatus.CONFLICT, "PASSWORD400", "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.NOT_FOUND, "PASSWORD401", "유효하지 않는 비밀번호입니다."),

    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "EMAIL400", "이미 회원가입 된 이메일입니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER400", "존재하지 않는 회원입니다."),

    JWT_MALFORMED(HttpStatus.UNAUTHORIZED, "TOKEN400", "JWT 토큰이 올바르지 않은 형식입니다."),
    INVALID_TOKEN(HttpStatus.NOT_FOUND, "TOKEN401", "유효하지 않은 토큰입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDto getReason() {
        return ErrorReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDto getReasonHttpStatus() {
        return ErrorReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}