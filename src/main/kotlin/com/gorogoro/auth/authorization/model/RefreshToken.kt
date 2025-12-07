package com.gorogoro.auth.authorization.model

import java.time.Instant

class RefreshToken(
    val id: Long? = null,
    val userId: Long,
    refreshToken: String,
    refreshTokenExpire: Instant,
    ) {
    var refreshToken: String = refreshToken
        protected set

    var refreshTokenExpire: Instant = refreshTokenExpire
        protected set

    fun changeToken(token : String,refreshTokenExpire: Instant) {
        this.refreshToken = token
        this.refreshTokenExpire = refreshTokenExpire
    }

    fun isExpired(): Boolean {
        return Instant.now().isAfter(refreshTokenExpire)
    }
}
