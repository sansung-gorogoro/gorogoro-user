package com.gorogoro.auth.user.application.dto

data class UpdateUserCommand(
    val userId: Long,
    val email: String?,
    var passwordEncrypted : String?,
    val name: String?,
    val nickname: String?,
)
