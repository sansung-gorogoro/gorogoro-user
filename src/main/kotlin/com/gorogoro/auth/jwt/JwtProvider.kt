package com.gorogoro.auth.jwt

import com.gorogoro.auth.user.domain.Role
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.time.Instant
import java.util.*

@Component
class JwtProvider(
    @Value("\${jwt.expiration-ms}")
    private val accessExpirationSeconds: Long,
    @Value("\${jwt.refresh-expiration-ms}")
    private val refreshExpirationDays: Long,
    @Value("classpath:private_key.pem")
    private val privateKeyString: String,
) {
    private val ROLE = "role"
    private val encrypt = "RSA"
    private val privateKey: PrivateKey by lazy {
        val privateKeyPEM = String(privateKeyString.toByteArray())
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replace("\\s".toRegex(), "")

        val keySpec = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyPEM))
        KeyFactory.getInstance(encrypt).generatePrivate(keySpec)
    }

    fun createAccessToken(userId: Long, role: Role): String {
        val expired = Instant.now().plusSeconds(accessExpirationSeconds)
        return Jwts.builder()
            .subject(userId.toString())
            .claim(ROLE, role.name)
            .issuedAt(Date.from(Instant.now()))
            .expiration(Date.from(expired))
            .signWith(privateKey, Jwts.SIG.RS256)
            .compact()

    }

    fun createRefreshToken(): Pair<String, Instant> {
        val now = Instant.now()
        val validity = now.plusSeconds(refreshExpirationDays)

        val token = Jwts.builder()
            .issuedAt(Date.from(now))
            .expiration(Date.from(validity))
            .signWith(privateKey, Jwts.SIG.RS256)
            .compact()

        return Pair(token, validity)
    }
}
