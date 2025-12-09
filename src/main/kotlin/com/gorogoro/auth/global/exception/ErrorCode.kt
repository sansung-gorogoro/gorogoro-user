package com.gorogoro.auth.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {
    //토큰 에러
    TOKEN_NOT_START_BEARER(HttpStatus.UNAUTHORIZED, "요청에 인증 토큰이 포함되지 않았습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND,"리프레시 토큰을 찾을수 없습니다. 확인 후 다시 시도해주세요."),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "토큰이 존재하지 않습니다."),
    INVALID_TOKEN_FORMAT(HttpStatus.UNAUTHORIZED, "올바르지 않은 토큰 형식 입니다."),

    //유저 에러
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "아이디/비밀번호가 유효하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST,"아이디/비밀번호가 유효하지 않습니다."),
    INACTIVE_USER(HttpStatus.BAD_REQUEST,"휴면 계졍입니다. 이메일 인증을 통해 활성화 시켜주세요."),
    INVALID_NICKNAME(HttpStatus.BAD_REQUEST,"닉네임이 유효하지 않습니다."),
    FAILURE_CREATED_NICKNAME(HttpStatus.INTERNAL_SERVER_ERROR,"닉네임 생성에 실패했습니다. 잠시후 다시 시도해주세요."),
    CANT_USE_SPECIAL_CHAR(HttpStatus.BAD_REQUEST,"특수문자는 공백만 사용할 수 있습니다."),
    STRING_LENGTH_TOO_MUCH(HttpStatus.BAD_REQUEST,"문자열이 너무 깁니다. 다시 입력해 주세요."),
    STRING_CANT_BE_BLANK(HttpStatus.BAD_REQUEST,"공백은 입력 될 수 없습니다."),
    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST,"중복된 닉네임 입니다. 다시 입력해 주세요."),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST,"이메일 형식이 올바르지 않습니다."),
    PASSWORD_SAME_PREV(HttpStatus.BAD_REQUEST,"비밀번호는 이전과 같은 비밀번호로 변경할 수 없습니다."),
    NICKNAME_SAME_PREV(HttpStatus.BAD_REQUEST,"닉네임은 이전과 같습니다. 다시 입력해 주세요"),
    USER_STATUS_IS_NOT_VALID(HttpStatus.BAD_REQUEST,"해당 유저는 휴면 또는 삭제 되었습니다."),
}
