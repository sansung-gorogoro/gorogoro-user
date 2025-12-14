package com.gorogoro.auth.authorization.application.dto

import com.gorogoro.auth.user.model.constant.Role
import jakarta.validation.constraints.NotBlank

data class LoginCommand(
    @field:NotBlank
    val email: String,

    @field:NotBlank
    val password: String
)

data class LoginResultResponse(
    val nickname: String,
    val role: Role,
    val accessToken: String,
    val refreshToken: String
)
