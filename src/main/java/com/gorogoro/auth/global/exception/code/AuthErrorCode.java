package com.gorogoro.auth.global.exception.code;

import com.gorogoro.auth.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    ;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
