package com.gorogoro.auth.authorization.adapter.out.persistence

import com.gorogoro.auth.authorization.application.port.out.CheckUserPort
import com.gorogoro.auth.authorization.application.port.out.LoadUserPort
import com.gorogoro.auth.authorization.application.port.out.SaveUserPort
import com.gorogoro.auth.user.model.User
import com.gorogoro.auth.user.infra.adapter.out.persistence.UserJpaRepository
import org.springframework.stereotype.Repository

@Repository
class AuthPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository,
) : LoadUserPort, SaveUserPort, CheckUserPort {

    override fun findByEmail(email: String): User? = userJpaRepository.findByEmail(email)

    override fun findById(id: Long): User? = userJpaRepository.findById(id).orElse(null)

    override fun saveUser(user: User): User = userJpaRepository.save(user)

    override fun existsByNickname(nickname: String): Boolean = userJpaRepository.existsByNickname(nickname)
}
