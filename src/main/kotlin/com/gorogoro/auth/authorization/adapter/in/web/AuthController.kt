package com.gorogoro.auth.authorization.adapter.`in`.web

import com.gorogoro.auth.authorization.application.dto.AccessTokenResponse
import com.gorogoro.auth.authorization.application.dto.LoginCommand
import com.gorogoro.auth.authorization.application.dto.LoginResultResponse
import com.gorogoro.auth.authorization.application.dto.RefreshTokenCommand
import com.gorogoro.auth.authorization.application.dto.SignupCommand
import com.gorogoro.auth.authorization.application.port.`in`.LoginUseCase
import com.gorogoro.auth.authorization.application.port.`in`.RefreshTokenUseCase
import com.gorogoro.auth.authorization.application.port.`in`.SignupUseCase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val signupUseCase: SignupUseCase,
    private val loginUseCase: LoginUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
) {

    @PostMapping("/register")
    fun signup(@Valid @RequestBody command: SignupCommand): ResponseEntity<Unit> {
        signupUseCase.signup(command)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody command: LoginCommand): ResponseEntity<LoginResultResponse> {
        val tokenResponse = loginUseCase.login(command)
        return ResponseEntity.ok(tokenResponse)
    }

    @PostMapping("/refresh")
    fun refreshAccessToken(@Valid @RequestBody command: RefreshTokenCommand): ResponseEntity<AccessTokenResponse> {
        val tokenResponse = refreshTokenUseCase.refresh(command)
        return ResponseEntity.ok(tokenResponse)
    }
}