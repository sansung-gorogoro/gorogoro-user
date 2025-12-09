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
        validateEmail(this.email)
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

    fun updatePassword(newPassword: String, newPasswordEncrypted: String) {
        validateRawPassword(newPassword)
        this.passwordEncrypted = newPasswordEncrypted
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

    private fun validateNickname(newNickname: String) {
        nonBlankString(newNickname)
        validateSpecialChar(newNickname)
        validateStrLength(newNickname, NICKNAME_MAX_LENGTH)
    }

    private fun validateName(name: String) {
        nonBlankString(name)
        validateNameInSpecialChar(name)
        validateStrLength(name, NAME_MAX_LENGTH)
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

    companion object {
        private val EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$".toRegex()
        private val NICKNAME_MAX_LENGTH = 10
        private val NAME_MAX_LENGTH = 7
        private val NAME_REGEX = "^[가-힣]+\$".toRegex()
        private val SPECIAL_CHAR = "^[가-힣a-zA-Z0-9 ]+\$".toRegex()

        val PASSWORD_POLICY_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$".toRegex()

        private fun validateRawPassword(rawPassword: String) {
            if (!rawPassword.matches(PASSWORD_POLICY_REGEX)) {
                throw BusinessException.builder(ErrorCode.INVALID_PASSWORD).build()
            }
        }

        private fun validateSpecialChar(str: String) {
            if (!str.matches(SPECIAL_CHAR)) {
                throw BusinessException.builder(ErrorCode.CANT_USE_SPECIAL_CHAR).build()
            }
        }

        private fun validateNameInSpecialChar(str: String) {
            if (!str.matches(NAME_REGEX)) {
                throw BusinessException.builder(ErrorCode.CANT_USE_SPECIAL_CHAR).build()
            }
        }

        private fun validateEmail(newEmail: String) {
            if (!newEmail.matches(EMAIL_REGEX)) {
                throw BusinessException.builder(ErrorCode.INVALID_EMAIL_FORMAT).build()
            }
        }
    }
}
