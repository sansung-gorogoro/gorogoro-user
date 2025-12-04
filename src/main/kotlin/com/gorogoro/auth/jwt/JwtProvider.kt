package com.gorogoro.auth.jwt

import com.gorogoro.auth.user.domain.Role
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import javax.crypto.SecretKey
import io.jsonwebtoken.security.Keys
import java.time.Instant
import java.util.Date

@Component
class JwtProvider(
    @Value("\${jwt.secret}")
    private val secretKey: String,
    @Value("\${jwt.expiration-ms}")
    private val accessExpirationHours: Long,
    @Value("\${jwt.refresh-expiration-ms}")
    private val refreshExpirationDays: Long
) {
    private val key: SecretKey by lazy {
        Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
    }

    fun createAccessToken(userId: Long, role: Role): String {
        val now = Instant.now()
        val validity = now.plusSeconds(accessExpirationHours)

        return Jwts.builder()
            .subject(userId.toString())
            .claim("role", role.name)
            .issuedAt(Date.from(now))
            .expiration(Date.from(validity))
            .signWith(key)
            .compact()
    }

    fun createRefreshToken(): Pair<String, Instant> {
        val now = Instant.now()
        val validity = now.plusSeconds(refreshExpirationDays) // 매우 긴 시간

        val token = Jwts.builder()
            .issuedAt(Date.from(now))
            .expiration(Date.from(validity))
            .signWith(key)
            .compact()

        return Pair(token, validity)
    }

    fun getUserIdIfValid(token: String): Long {
        val claims: Claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload

        return claims.subject.toLong()
    }
}
