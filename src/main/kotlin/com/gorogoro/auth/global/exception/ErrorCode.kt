package com.gorogoro.auth.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {
    TOKEN_NOT_START_BEARER(HttpStatus.UNAUTHORIZED, "요청에 인증 토큰이 포함되지 않았습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    INVALID_TOKEN_FORMAT(HttpStatus.UNAUTHORIZED, "올바르지 않은 토큰 형식 입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "아이디/비밀번호가 유효하지 않습니다."),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "토큰이 존재하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST,"아이디/비밀번호가 유효하지 않습니다."),
    INVALID_NICKNAME(HttpStatus.BAD_REQUEST,"닉네임이 유효하지 않습니다."),
    FAILURE_CREATED_NICKNAME(HttpStatus.INTERNAL_SERVER_ERROR,"닉네임 생성에 실패했습니다. 잠시후 다시 시도해주세요."),
}
