package com.gorogoro.auth.authorization.application.port.out

import com.gorogoro.auth.authorization.model.RefreshToken
import org.springframework.stereotype.Component

@Component
interface RefreshTokenPort {
    fun save(refreshToken: RefreshToken): RefreshToken
    fun findByRefreshToken(refreshToken: String): RefreshToken?
    fun deleteRefreshTokenById(id: Long)
}
