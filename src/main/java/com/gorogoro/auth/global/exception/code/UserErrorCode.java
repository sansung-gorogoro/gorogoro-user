package com.gorogoro.auth.global.exception.code;

import com.gorogoro.auth.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    /* 조회 관련 */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USR-0001", "사용자를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "USR-0002", "이미 존재하는 사용자입니다."),

    STRING_CANT_BE_BLANK(HttpStatus.BAD_REQUEST, "USR-0020", "값은 비어 있을 수 없습니다."),
    STRING_LENGTH_TOO_MUCH(HttpStatus.BAD_REQUEST, "USR-0021", "문자열 길이가 허용 범위를 초과했습니다."),
    COMPETE_KOREAN_NAME(HttpStatus.BAD_REQUEST, "USR-0025", "이름은 한글만 입력할 수 있습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
