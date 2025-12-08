package com.gorogoro.auth.user.application.port.`in`

import com.gorogoro.auth.user.application.dto.GetUserCommand
import com.gorogoro.auth.user.model.User

interface GetUserUseCase{
    fun getUserInfo(command: GetUserCommand): User?
}
