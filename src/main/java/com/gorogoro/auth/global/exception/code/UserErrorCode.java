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
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "USR-0002", "이미 존재하는 사용자입니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
