package com.gorogoro.auth.user.application.port.out

interface CheckUserPort {
    fun existsByNickname(nickname: String): Boolean
}