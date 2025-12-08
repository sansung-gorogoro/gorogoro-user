package com.gorogoro.auth.authorization.application.port.out

interface CheckUserPort {
    fun existsByNickname(nickname: String): Boolean
}
