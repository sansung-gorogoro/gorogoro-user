package com.gorogoro.auth.user.model

import com.gorogoro.auth.global.exception.BusinessException
import com.gorogoro.auth.global.exception.ErrorCode
import com.gorogoro.auth.user.model.constant.Role
import com.gorogoro.auth.user.model.constant.Status
import java.time.Instant

class User(
    val id: Long = 0,
    email: String,
    passwordEncrypted: String,
    name: String,
    nickname: String,
    role: Role,
    status: Status = Status.ACTIVATED,
    lastLoginAt: Instant? = null,
    val createdAt: Instant,
    modifiedAt: Instant,
    deletedAt: Instant? = null
) {
    var passwordEncrypted: String = passwordEncrypted
        protected set
    var email: String = email
        protected set
    var name: String = name
        protected set
    var nickname: String = nickname
        protected set
    var role: Role = role
        protected set
    var status: Status = status
        protected set
    var lastLoginAt: Instant? = lastLoginAt
        protected set
    var modifiedAt: Instant = modifiedAt
        protected set
    var deletedAt: Instant? = deletedAt
        protected set

    init {
        validateNickname(this.nickname)
        validateName(this.name)
    }

    fun lastLogin(time: Instant) {
        this.lastLoginAt = time
    }

    fun updateEmail(newEmail: String) {
        validateEmail(newEmail)
        this.email = newEmail
        updateUserDate()
    }

    fun updatePassword(newPassword: String) {
        this.passwordEncrypted = newPassword
        updateUserDate()
    }

    fun updateNickname(newNickname: String) {
        validateNickname(newNickname)
        this.nickname = newNickname
        updateUserDate()
    }

    fun updateName(name: String) {
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

    private fun validateEmail(newEmail: String) {
        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$".toRegex()
        nonBlankString(newEmail)
        if (!newEmail.matches(emailRegex)) {
            throw BusinessException.builder(ErrorCode.INVALID_EMAIL_FORMAT).build()
        }
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
            throw BusinessException.builder(ErrorCode.STRING_CANT_BE_BLANK).build()
        }
    }

    private fun validateStrLength(str: String, length: Int) {
        if (str.length > length) {
            throw BusinessException.builder(ErrorCode.STRING_LENGTH_TOO_MUCH).build()
        }
    }

    private fun validateSpecialChar(str: String) {
        val validCharRegex = "^[가-힣a-zA-Z ]+\$".toRegex()

        if (!str.matches(validCharRegex)) {
            throw BusinessException.builder(ErrorCode.USER_NAME_CANT_USE_SPECIAL_CHAR).build()
        }
    }
}
