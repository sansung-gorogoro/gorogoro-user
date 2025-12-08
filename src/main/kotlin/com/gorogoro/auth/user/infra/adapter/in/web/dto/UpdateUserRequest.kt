package com.gorogoro.auth.user.infra.adapter.`in`.web.dto

data class UpdateUserRequest(
    val email: String?,
    var passwordEncrypted : String?,
    val name: String?,
    val nickname: String?,
)