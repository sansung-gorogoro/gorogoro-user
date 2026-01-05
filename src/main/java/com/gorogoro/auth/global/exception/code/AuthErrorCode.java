package com.gorogoro.auth.global.exception.code;

import com.gorogoro.auth.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "ATH-0001", "이메일 또는 비밀번호가 올바르지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "ATH-0002", "유효하지 않은 리프레시 토큰입니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "ATH-0003", "리프레시 토큰이 만료되었습니다. 다시 로그인해주세요.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
