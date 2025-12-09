package com.gorogoro.auth.authorization.application.service

import com.gorogoro.auth.authorization.application.dto.AccessTokenResponse
import com.gorogoro.auth.authorization.application.dto.LoginCommand
import com.gorogoro.auth.authorization.application.dto.LoginResultResponse
import com.gorogoro.auth.authorization.application.dto.RefreshTokenCommand
import com.gorogoro.auth.authorization.application.dto.SignupCommand
import com.gorogoro.auth.authorization.application.port.`in`.LoginUseCase
import com.gorogoro.auth.authorization.application.port.`in`.RefreshTokenUseCase
import com.gorogoro.auth.authorization.application.port.`in`.SignupUseCase
import com.gorogoro.auth.user.application.port.out.CheckNicknamePort
import com.gorogoro.auth.user.application.port.out.LoadUserPort
import com.gorogoro.auth.authorization.application.port.out.RefreshTokenPort
import com.gorogoro.auth.authorization.application.port.out.SaveUserPort
import com.gorogoro.auth.authorization.model.RefreshToken
import com.gorogoro.auth.global.exception.BusinessException
import com.gorogoro.auth.global.exception.ErrorCode
import com.gorogoro.auth.jwt.JwtProvider
import com.gorogoro.auth.user.application.port.out.NicknamePolicyPort
import com.gorogoro.auth.user.model.User
import com.gorogoro.auth.user.model.constant.Status
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class AuthService(
    private val loadUserPort: LoadUserPort,
    private val checkNicknamePort: CheckNicknamePort,
    private val saveUserPort: SaveUserPort,
    private val refreshTokenPort: RefreshTokenPort,
    private val jwtProvider: JwtProvider,
    private val passwordEncoder: PasswordEncoder,
    private val nicknameGenerator: NicknamePolicyPort,
) : LoginUseCase, SignupUseCase, RefreshTokenUseCase {

    @Transactional
    override fun signup(cmd: SignupCommand) {
        val encryptedPassword = passwordEncoder.encode(cmd.password)
        val uniqueNickname = generateUniqueNickname()
        val newUser = User(
            email = cmd.email,
            passwordEncrypted = encryptedPassword,
            name = cmd.name,
            nickname = uniqueNickname,
            role = cmd.role,
            createdAt = Instant.now(),
            modifiedAt = Instant.now(),
        )
        saveUserPort.saveUser(newUser)
    }

    @Transactional
    override fun login(cmd: LoginCommand): LoginResultResponse {
        val user = loadUserPort.findByEmail(cmd.email)
            ?: throw BusinessException.builder(ErrorCode.USER_NOT_FOUND).build()

        if (!passwordEncoder.matches(cmd.password, user.passwordEncrypted)) {
            throw BusinessException.builder(ErrorCode.INVALID_PASSWORD).build()
        }

        if(user.status!= Status.ACTIVATED){
            throw BusinessException.builder(ErrorCode.INACTIVE_USER).build()
        }

        val accessToken = jwtProvider.createAccessToken(user.id, user.role)

        val (refreshToken, expireDate) = jwtProvider.createRefreshToken()

        val refreshTokenEntity = RefreshToken(
            userId = user.id,
            refreshToken = refreshToken,
            refreshTokenExpire = expireDate
        )
        refreshTokenPort.save(refreshTokenEntity)

        user.lastLogin(Instant.now())

        saveUserPort.saveUser(user)

        return LoginResultResponse(user.nickname, user.role, accessToken, refreshToken)
    }

    @Transactional(noRollbackFor = [BusinessException::class])
    override fun refresh(cmd: RefreshTokenCommand): AccessTokenResponse {
        val savedRefreshToken = refreshTokenPort.findByRefreshToken(cmd.refreshToken)
            ?: throw BusinessException.builder(ErrorCode.REFRESH_TOKEN_NOT_FOUND).build()

        if (savedRefreshToken.isExpired()) {
            savedRefreshToken.id?.let { deleteRefreshToken(it) }
            throw BusinessException.builder(ErrorCode.TOKEN_EXPIRED).build()
        }

        val user = loadUserPort.findById(savedRefreshToken.userId)
            ?: throw BusinessException.builder(ErrorCode.USER_NOT_FOUND).build()

        if (user.status != Status.ACTIVATED) {
            savedRefreshToken.id?.let { deleteRefreshToken(it) }
            throw BusinessException.builder(ErrorCode.USER_STATUS_IS_NOT_VALID).build()
        }

        val accessToken = jwtProvider.createAccessToken(user.id, user.role)

        return AccessTokenResponse(accessToken)
    }

    private fun generateUniqueNickname(): String {
        var nickname: String
        var retryCount = 0
        val maxRetry = 5

        do {
            if (retryCount >= maxRetry) {
                throw BusinessException.builder(ErrorCode.FAILURE_CREATED_NICKNAME).build()
            }
            nickname = nicknameGenerator.generate()
            retryCount++

        } while (checkNicknamePort.existsByNickname(nickname))

        return nickname
    }

    private fun deleteRefreshToken(id: Long) {
        refreshTokenPort.deleteRefreshTokenById(id)
    }
}
