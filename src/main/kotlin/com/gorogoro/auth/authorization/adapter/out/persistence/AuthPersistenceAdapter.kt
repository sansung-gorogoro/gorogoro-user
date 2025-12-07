package com.gorogoro.auth.authorization.adapter.out.persistence

import com.gorogoro.auth.authorization.application.port.out.CheckUserPort
import com.gorogoro.auth.authorization.application.port.out.LoadUserPort
import com.gorogoro.auth.authorization.application.port.out.RefreshTokenPort
import com.gorogoro.auth.authorization.application.port.out.SaveUserPort
import com.gorogoro.auth.authorization.model.RefreshToken
import com.gorogoro.auth.user.domain.User
import com.gorogoro.auth.user.infra.adapter.out.persistence.UserJpaRepository
import org.springframework.stereotype.Repository

@Repository
class AuthPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository,
    private val refreshTokenJpaRepository: RefreshTokenJpaRepository
) : LoadUserPort, SaveUserPort, RefreshTokenPort, CheckUserPort {

    override fun findByEmail(email: String): User? = userJpaRepository.findByEmail(email)

    override fun findById(id: Long): User? = userJpaRepository.findById(id).orElse(null)

    override fun saveUser(user: User): User = userJpaRepository.save(user)

    override fun save(refreshToken: RefreshToken): RefreshToken =
        refreshTokenJpaRepository.save(refreshToken)

    override fun existsByNickname(nickname: String): Boolean = userJpaRepository.existsByNickname(nickname)

    override fun findByRefreshToken(refreshToken: String): RefreshToken? = refreshTokenJpaRepository.findByRefreshToken(refreshToken)

    override fun deleteRefreshTokenById(id: Long) = refreshTokenJpaRepository.deleteById(id)
}