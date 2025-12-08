package com.gorogoro.auth.user.application.port.out

import com.gorogoro.auth.user.model.User

interface ModifyUserPort {
    fun modifyUserInfo(user: User) : User
}
