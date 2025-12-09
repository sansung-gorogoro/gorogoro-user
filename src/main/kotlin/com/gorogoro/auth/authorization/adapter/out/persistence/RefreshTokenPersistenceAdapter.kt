package com.gorogoro.auth.authorization.adapter.out.persistence

import com.gorogoro.auth.authorization.adapter.out.persistence.entity.RefreshTokenJpaEntity
import com.gorogoro.auth.authorization.application.port.out.RefreshTokenPort
import com.gorogoro.auth.authorization.model.RefreshToken
import com.gorogoro.auth.global.exception.BusinessException
import com.gorogoro.auth.global.exception.ErrorCode
import org.springframework.stereotype.Component

@Component
class RefreshTokenPersistenceAdapter(
    private val refreshTokenJpaRepository: RefreshTokenJpaRepository
) : RefreshTokenPort {

    override fun save(refreshToken: RefreshToken) {
        val entity = RefreshTokenJpaEntity.from(refreshToken)
        refreshTokenJpaRepository.save(entity)
    }

    override fun findByRefreshToken(token: String): RefreshToken? {
        val entity = refreshTokenJpaRepository.findByRefreshToken(token)
            ?: throw BusinessException.builder(ErrorCode.TOKEN_NOT_FOUND).build()
        return entity.toDomain()
    }

    override fun deleteRefreshTokenById(id: Long) = refreshTokenJpaRepository.deleteById(id)
}