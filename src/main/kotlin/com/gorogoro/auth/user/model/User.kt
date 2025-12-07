package com.gorogoro.auth.user.domain

import com.gorogoro.auth.global.exception.BusinessException
import com.gorogoro.auth.global.exception.ErrorCode
import com.gorogoro.auth.user.model.constant.Role
import com.gorogoro.auth.user.model.constant.Status
import java.time.Instant

class User(
    val id: Long,
    var email: String,
    val passwordEncrypted: String,
    var name: String,
    var nickname: String,
    var role: Role,
    var status: Status = Status.ACTIVATED,
    var lastLoginAt: Instant? = null,
    val createdAt: Instant,
    var modifiedAt: Instant,
    var deletedAt: Instant? = null
) {
    fun lastLogin(time: Instant) {
        this.lastLoginAt = time
    }
    fun updateNickname(newNickname: String) {
        if(newNickname.isBlank()){
            throw BusinessException.builder(ErrorCode.INVALID_NICKNAME).build()
        }
        this.nickname = newNickname
        updateUserDate()
    }
    fun changeStatus(status : Status) {
        this.status = status
        updateUserDate()
    }

    fun updateUserDate(){
        this.modifiedAt = Instant.now()
    }
}