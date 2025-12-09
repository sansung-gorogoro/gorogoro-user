package com.gorogoro.auth.authorization.application.port.`in`

import com.gorogoro.auth.authorization.application.dto.AccessTokenResponse
import com.gorogoro.auth.authorization.application.dto.RefreshTokenCommand

interface RefreshTokenUseCase {
    fun refresh(cmd: RefreshTokenCommand) : AccessTokenResponse
}