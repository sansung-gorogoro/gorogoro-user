package com.gorogoro.auth.user.infra.adapter.`in`.server

import com.gorogoro.auth.user.application.dto.GetUserCommand
import com.gorogoro.auth.user.application.port.`in`.GetUserUseCase
import com.gorogoro.auth.user.infra.adapter.`in`.server.response.NicknameResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/server/users")
class ServerController(
    private val getUserUseCase: GetUserUseCase,
) {
    @GetMapping("/nickname")
    fun getNickname(@RequestParam("userId") userId: Long): ResponseEntity<NicknameResponse>{
        val command = GetUserCommand(userId)
        val response = getUserUseCase.getUserNickname(command)
        return ResponseEntity.ok(response)
    }
}
