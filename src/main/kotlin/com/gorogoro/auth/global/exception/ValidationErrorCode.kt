package com.gorogoro.auth.global.exception

import org.springframework.http.HttpStatus

enum class ValidationErrorCode(
    val httpStatus: HttpStatus,
    val message: String,
    val errorCode: String,
    val targetField: String? = null
) {
    INVALID_INPUT_IN_EMAIL(HttpStatus.BAD_REQUEST, "이메일은 필수 입력 값입니다", "V-0001", "email"),
    INVALID_INPUT_IN_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호는 필수 입력 값입니다.", "V-0002", "password"),
    INVALID_INPUT_IN_NAME(HttpStatus.BAD_REQUEST, "이름은 필수 입력 값입니다.", "V-0003", "name"),
    INVALID_INPUT_IN_ROLE(HttpStatus.BAD_REQUEST, "권한은 필수 입력 값입니다.", "V-0004", "role"),

    INVALID_INPUT_IN_COMMAND(HttpStatus.BAD_REQUEST, "유효하지 않은 입력값입니다.", "V-0005", "command"),;

    companion object {
        fun findByField(field: String?): ValidationErrorCode? {
            return entries.find { it.targetField == field }
        }
    }
}