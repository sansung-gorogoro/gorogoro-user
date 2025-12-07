package com.gorogoro.auth.user.infra.adapter.out.persistence

import com.gorogoro.auth.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun existsByNickname(nickname : String) : Boolean
}
