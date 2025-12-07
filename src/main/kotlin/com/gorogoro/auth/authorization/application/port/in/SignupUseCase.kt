package com.gorogoro.auth.authorization.application.port.`in`

import com.gorogoro.auth.authorization.application.dto.SignupCommand
import org.springframework.stereotype.Component

@Component
interface SignupUseCase {
    fun signup(cmd: SignupCommand)
}
