package com.gorogoro.auth.user.application.port.out

interface CheckNicknamePort {
    fun existsByNickname(nickname: String): Boolean
}
