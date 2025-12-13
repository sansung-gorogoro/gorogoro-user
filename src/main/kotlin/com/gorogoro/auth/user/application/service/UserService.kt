package com.gorogoro.auth.user.application.service

import com.gorogoro.auth.global.exception.BusinessException
import com.gorogoro.auth.global.exception.ErrorCode
import com.gorogoro.auth.user.application.dto.GetUserCommand
import com.gorogoro.auth.user.application.dto.UpdateUserCommand
import com.gorogoro.auth.user.application.port.`in`.GetUserUseCase
import com.gorogoro.auth.user.application.port.`in`.ModifyUserUseCase
import com.gorogoro.auth.user.application.port.out.CheckNicknamePort
import com.gorogoro.auth.user.application.port.out.LoadUserPort
import com.gorogoro.auth.user.application.port.out.ModifyUserPort
import com.gorogoro.auth.user.infra.adapter.`in`.server.response.NicknameResponse
import com.gorogoro.auth.user.infra.adapter.`in`.web.response.FindUserResponse
import com.gorogoro.auth.user.model.constant.Status
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val checkNicknamePort: CheckNicknamePort,
    private val modifyUserPort: ModifyUserPort,
    private val loadUserPort: LoadUserPort,
    private val passwordEncoder: PasswordEncoder
) : GetUserUseCase, ModifyUserUseCase {
    override fun getUserInfo(command: GetUserCommand): FindUserResponse {
        val user = loadUserPort.findById(command.id)
            ?: throw BusinessException.builder(ErrorCode.USER_NOT_FOUND).build()
        return FindUserResponse(
            id = user.id,
            username = user.name,
            nickname = user.nickname,
            email = user.email,
            role = user.role.name,
            createAt = user.createdAt,
            )
    }

    override fun getUserNickname(command: GetUserCommand): NicknameResponse {
        val user = loadUserPort.findById(command.id)
        ?: throw BusinessException.builder(ErrorCode.USER_NOT_FOUND).build()
        return NicknameResponse(
            nickname = user.nickname,
        )
    }

    override fun updateUserInfo(command: UpdateUserCommand) {
        val user = loadUserPort.findById(command.userId)
            ?: throw BusinessException.builder(ErrorCode.USER_NOT_FOUND).build()

        user.apply {
            command.email?.let { user.updateEmail(it) }

            command.name?.let { user.updateName(it) }

            command.nickname?.let { newNickname ->
                if (nickname != newNickname) {
                    if (checkNicknamePort.existsByNickname(newNickname)) {
                        throw BusinessException.builder(ErrorCode.DUPLICATED_NICKNAME).build()
                    }
                    updateNickname(newNickname)
                } else {
                    throw BusinessException.builder(ErrorCode.NICKNAME_SAME_PREV).build()
                }
            }

            command.passwordEncrypted?.let {
                if (passwordEncoder.matches(it, user.passwordEncrypted)) {
                    throw BusinessException.builder(ErrorCode.PASSWORD_SAME_PREV).build()
                }
                val newEncryptedPassword = passwordEncoder.encode(it)
                user.updatePassword(it, newEncryptedPassword)
            }
        }

        modifyUserPort.modifyUserInfo(user)
    }

    override fun leaveUser(userId: Long) {
        val user = loadUserPort.findById(userId)
            ?: throw BusinessException.builder(ErrorCode.USER_NOT_FOUND).build()

        user.changeStatus(Status.DELETED)

        modifyUserPort.modifyUserInfo(user)
    }
}
