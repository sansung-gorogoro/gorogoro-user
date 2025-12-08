package com.gorogoro.auth.user.application.port.out

import com.gorogoro.auth.user.infra.persistence.entity.UserJpaEntity

interface SaveUserPort {
    fun saveUser(user: UserJpaEntity)
}