package com.gorogoro.auth.authorization.application.dto

import com.gorogoro.auth.user.model.constant.Role
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class SignupCommand(
    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    @field:NotBlank(message = "이메일은 필수 입력 값입니다.")
    val email: String,

    @field:NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    val password: String,

    @field:NotBlank(message = "이름은 필수 입력 값입니다.")
    val name: String,

    @field:NotNull(message = "권한(Role)은 필수 입력 값입니다.")
    val role: Role
)
