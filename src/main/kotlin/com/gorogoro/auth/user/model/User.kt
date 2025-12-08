package com.gorogoro.auth.user.model

import com.gorogoro.auth.global.exception.BusinessException
import com.gorogoro.auth.global.exception.ErrorCode
import com.gorogoro.auth.user.model.constant.Role
import com.gorogoro.auth.user.model.constant.Status
import java.time.Instant

class User(
    val id: Long = 0,
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
        validateNickname(newNickname)
        this.nickname = newNickname
        updateUserDate()
    }

    fun updateUserName(name: String) {
        validateName(name)
        this.name = name
        updateUserDate()
    }

    fun changeStatus(status: Status) {
        this.status = status
        updateUserDate()
    }

    fun updateUserDate(now: Instant = Instant.now()) {
        this.modifiedAt = now
    }

    private fun validateNickname(newNickname: String) {
        val nickNameMaxLength = 10
        nonBlankString(newNickname)
        validateSpecialChar(newNickname)
        validateStrLength(newNickname, nickNameMaxLength)
    }

    private fun validateName(name: String) {
        val nameMaxLength = 7
        nonBlankString(name)
        validateSpecialChar(name)
        validateStrLength(name, nameMaxLength)
    }

    private fun nonBlankString(str: String) {
        if (str.isBlank()) {
            throw BusinessException.builder(ErrorCode.INVALID_NICKNAME).build()
        }
    }

    private fun validateStrLength(str: String, length: Int) {
        if (str.length > length) {
            throw BusinessException.builder(ErrorCode.INVALID_NICKNAME).build()
        }
    }

    private fun validateSpecialChar(str: String) {
        val validCharRegex = "^[가-힣a-zA-Z ]+\$".toRegex()

        if (!nickname.matches(validCharRegex)) {
            throw BusinessException.builder(ErrorCode.INVALID_NICKNAME).build()
        }
    }
}