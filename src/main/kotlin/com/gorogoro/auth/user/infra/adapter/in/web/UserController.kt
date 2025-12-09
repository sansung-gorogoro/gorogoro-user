package com.gorogoro.auth.user.infra.adapter.`in`.web

import com.gorogoro.auth.user.application.dto.UpdateUserCommand
import com.gorogoro.auth.user.application.port.`in`.ModifyUserUseCase
import com.gorogoro.auth.user.infra.adapter.`in`.web.dto.UpdateUserRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val modifyUserUseCase: ModifyUserUseCase
) {

    @PatchMapping("/modify")
    fun updateUser(
        @RequestHeader("X-User-Id") userId: Long,
        @RequestBody request: UpdateUserRequest
    ): ResponseEntity<Void> {
        val command = UpdateUserCommand(
            userId = userId,
            name = request.name,
            nickname = request.nickname,
            email = request.email,
            passwordEncrypted = request.passwordEncrypted
        )

        modifyUserUseCase.updateUserInfo(command)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/leave")
    fun withdrawUser(
        @RequestHeader("X-User-Id") userId: Long,
    ): ResponseEntity<Void> {
        modifyUserUseCase.leaveUser(userId)
        return ResponseEntity.ok().build()
    }
}