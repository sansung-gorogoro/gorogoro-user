package com.gorogoro.auth.user.application.service

import com.gorogoro.auth.user.application.port.`in`.GetUserUseCase
import com.gorogoro.auth.user.application.port.out.CheckUserPort
import com.gorogoro.auth.user.application.port.out.LoadUserPort
import com.gorogoro.auth.user.application.port.out.ModifyUserPort
import com.gorogoro.auth.user.application.port.out.SaveUserPort
import com.gorogoro.auth.user.infra.adapter.out.persistence.UserJpaRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userJpaRepository: UserJpaRepository,
    private val checkUserPort: CheckUserPort,
    private val saveUserPort: SaveUserPort,
    private val modifyUserPort: ModifyUserPort,
    private val loadUserPort: LoadUserPort,
): GetUserUseCase {

}