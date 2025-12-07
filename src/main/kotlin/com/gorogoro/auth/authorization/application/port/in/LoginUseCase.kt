package com.gorogoro.auth.authorization.application.port.`in`

import com.gorogoro.auth.authorization.application.dto.LoginCommand
import com.gorogoro.auth.authorization.application.dto.TokenResponse

interface LoginUseCase {
    fun login(cmd: LoginCommand): TokenResponse
}