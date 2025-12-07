package com.gorogoro.auth.authorization.application.port.out

import com.gorogoro.auth.user.domain.User
import org.springframework.stereotype.Component

@Component
interface SaveUserPort {
    fun saveUser(user: User): User
}
