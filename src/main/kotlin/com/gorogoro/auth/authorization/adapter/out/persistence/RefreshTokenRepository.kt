package com.gorogoro.auth.authorization.adapter.out.persistence

import com.gorogoro.auth.authorization.model.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenJpaRepository : JpaRepository<RefreshToken, Long>{
    fun findByRefreshToken(refreshToken : String) : RefreshToken?
}
