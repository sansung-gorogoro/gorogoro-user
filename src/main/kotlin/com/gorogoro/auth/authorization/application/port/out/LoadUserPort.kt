package com.gorogoro.auth.authorization.application.port.out

import com.gorogoro.auth.user.domain.User
import org.springframework.stereotype.Component

@Component
interface LoadUserPort {
    fun findByEmail(email: String): User?
    fun findById(id: Long): User?
}
