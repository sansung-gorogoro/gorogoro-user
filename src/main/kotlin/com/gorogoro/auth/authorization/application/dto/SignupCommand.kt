package com.gorogoro.auth.authorization.application.dto

import com.gorogoro.auth.user.model.constant.Role
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class SignupCommand(
    @field:NotBlank
    val email: String,

    @field:NotBlank
    val password: String,

    @field:NotBlank
    val name: String,

    @field:NotNull
    val role: Role
)
