package com.gorogoro.auth.user.application.port.`in`

import com.gorogoro.auth.user.application.dto.GetUserCommand
import com.gorogoro.auth.user.infra.adapter.`in`.server.response.NicknameResponse
import com.gorogoro.auth.user.infra.adapter.`in`.web.response.FindUserResponse
import com.gorogoro.auth.user.model.User

interface GetUserUseCase{
    fun getUserInfo(command: GetUserCommand): FindUserResponse
    fun getUserNickname(command: GetUserCommand): NicknameResponse
}
