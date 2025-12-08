package com.gorogoro.auth.authorization.application.port.out

import com.gorogoro.auth.user.model.User

interface SaveUserPort {
    fun saveUser(user: User): User
}
