package com.gorogoro.auth.user.infra.adapter.out.persistence

import com.gorogoro.auth.user.infra.persistence.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserJpaEntity, Long> {
    fun findByEmail(email: String): UserJpaEntity?
    fun existsByNickname(nickname : String) : Boolean
}
