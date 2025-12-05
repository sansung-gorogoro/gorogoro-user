package com.gorogoro.auth.authorization.application.port.out

import org.springframework.stereotype.Component

@Component
interface CheckUserPort {
    fun existsByNickname(nickname: String): Boolean
}
