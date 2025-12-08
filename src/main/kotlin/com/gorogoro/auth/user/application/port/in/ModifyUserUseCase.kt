package com.gorogoro.auth.user.application.port.`in`

import com.gorogoro.auth.user.application.dto.UpdateUserCommand

interface ModifyUserUseCase {
    fun updateUserInfo(command: UpdateUserCommand)
    fun leaveUser(userId: Long)
}