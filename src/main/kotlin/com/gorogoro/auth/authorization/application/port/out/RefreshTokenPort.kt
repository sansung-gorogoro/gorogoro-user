package com.gorogoro.auth.authorization.application.port.out

import com.gorogoro.auth.authorization.model.RefreshToken

interface RefreshTokenPort {
    fun save(refreshToken: RefreshToken)
    fun findByRefreshToken(refreshToken: String): RefreshToken?
    fun deleteRefreshTokenById(id: Long)
}
