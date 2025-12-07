package com.gorogoro.auth.authorization.application.port.`in`

import com.gorogoro.auth.authorization.application.dto.SignupCommand

interface SignupUseCase {
    fun signup(cmd: SignupCommand)
}
