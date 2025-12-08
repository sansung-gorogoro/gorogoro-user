package com.gorogoro.auth.jwt

import com.gorogoro.auth.user.model.constant.Role
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import org.springframework.util.StreamUtils
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.time.Instant
import java.util.Base64
import java.util.Date

@Component
class JwtProvider(
    @Value("\${jwt.expiration-seconds}")
    private val accessExpirationSeconds: Long,
    @Value("\${jwt.refresh-expiration-seconds}")
    private val refreshExpirationSeconds: Long,
    @Value("classpath:private_key.pem")
    private val privateKeyResource: Resource,
) {
    companion object {
        private const val ROLE_KEY = "role"
        private const val ALGORITHM = "RSA"
        private const val KEY_HEADER = "-----BEGIN PRIVATE KEY-----"
        private const val KEY_FOOTER = "-----END PRIVATE KEY-----"
    }

    private val privateKey: PrivateKey by lazy {
        val privateKeyString = StreamUtils.copyToString(privateKeyResource.inputStream, StandardCharsets.UTF_8)

        val privateKeyPEM = privateKeyString
            .replace(KEY_HEADER, "")
            .replace(KEY_FOOTER, "")
            .replace("\\s".toRegex(), "")

        val keySpec = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyPEM))
        KeyFactory.getInstance(ALGORITHM).generatePrivate(keySpec)
    }

    fun createAccessToken(userId: Long, role: Role): String {
        val expired = Instant.now().plusSeconds(accessExpirationSeconds)

        return Jwts.builder()
            .subject(userId.toString())
            .claim(ROLE_KEY, role.name)
            .issuedAt(Date.from(Instant.now()))
            .expiration(Date.from(expired))
            .signWith(privateKey, Jwts.SIG.RS256)
            .compact()
    }

    fun createRefreshToken(): Pair<String, Instant> {
        val now = Instant.now()
        val validity = now.plusSeconds(refreshExpirationSeconds)

        val token = Jwts.builder()
            .issuedAt(Date.from(now))
            .expiration(Date.from(validity))
            .signWith(privateKey, Jwts.SIG.RS256)
            .compact()

        return Pair(token, validity)
    }
}
