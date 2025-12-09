package com.gorogoro.auth.authorization.adapter.out.persistence

import com.gorogoro.auth.authorization.adapter.out.persistence.entity.RefreshTokenJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenJpaRepository : JpaRepository<RefreshTokenJpaEntity, Long>{
    fun findByRefreshToken(refreshToken : String) : RefreshTokenJpaEntity?
}
