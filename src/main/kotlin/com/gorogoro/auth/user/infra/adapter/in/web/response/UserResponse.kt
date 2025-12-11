package com.gorogoro.auth.user.infra.adapter.`in`.web.response

import java.time.Instant

data class FindUserResponse(
    val id: Long,
    val username: String,
    val nickname: String,
    val email: String,
    val role: String,
    val createAt: Instant,
)

data class ConvertNicknameResponse(
    val nickname: String,
)
