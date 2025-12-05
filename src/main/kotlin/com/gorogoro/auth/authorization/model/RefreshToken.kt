package com.gorogoro.auth.authorization.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.Instant

@Entity
class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
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
