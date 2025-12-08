package com.gorogoro.auth.user.application.port.out

import com.gorogoro.auth.user.model.User

interface LoadUserPort {
    fun findByEmail(email: String): User?
    fun findById(id: Long): User?
}
