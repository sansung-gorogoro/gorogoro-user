package com.gorogoro.auth.user.infra.adapter.out.persistence.adapter

import com.gorogoro.auth.global.exception.BusinessException
import com.gorogoro.auth.global.exception.ErrorCode
import com.gorogoro.auth.user.application.port.out.CheckUserPort
import com.gorogoro.auth.user.application.port.out.LoadUserPort
import com.gorogoro.auth.user.application.port.out.ModifyUserPort
import com.gorogoro.auth.user.application.port.out.SaveUserPort
import com.gorogoro.auth.user.infra.adapter.out.persistence.UserJpaRepository
import com.gorogoro.auth.user.infra.persistence.entity.UserJpaEntity
import com.gorogoro.auth.user.infra.persistence.entity.toDomain
import com.gorogoro.auth.user.model.User
import org.springframework.stereotype.Repository

@Repository
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository,
): LoadUserPort, CheckUserPort, SaveUserPort, ModifyUserPort {
    override fun findByEmail(email: String): User{
        val user = userJpaRepository.findByEmail(email) ?: throw BusinessException.builder(ErrorCode.USER_NOT_FOUND).build()
        return user.toDomain()
    }

    override fun findById(id: Long): User? = userJpaRepository.findById(id).orElse(null).toDomain()

    override fun existsByNickname(nickname: String): Boolean = userJpaRepository.existsByNickname(nickname)

    override fun saveUser(user: UserJpaEntity) {
        userJpaRepository.save(user)
    }
}