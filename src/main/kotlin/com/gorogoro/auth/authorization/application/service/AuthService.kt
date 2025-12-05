package com.gorogoro.auth.authorization.application.service

import com.gorogoro.auth.authorization.application.dto.*
import com.gorogoro.auth.authorization.application.port.`in`.LoginUseCase
import com.gorogoro.auth.authorization.application.port.`in`.RefreshTokenUseCase
import com.gorogoro.auth.authorization.application.port.`in`.SignupUseCase
import com.gorogoro.auth.authorization.application.port.out.CheckUserPort
import com.gorogoro.auth.authorization.application.port.out.LoadUserPort
import com.gorogoro.auth.authorization.application.port.out.RefreshTokenPort
import com.gorogoro.auth.authorization.application.port.out.SaveUserPort
import com.gorogoro.auth.authorization.model.RefreshToken
import com.gorogoro.auth.global.exception.BusinessException
import com.gorogoro.auth.global.exception.ErrorCode
import com.gorogoro.auth.jwt.JwtProvider
import com.gorogoro.auth.user.common.NicknameGenerator
import com.gorogoro.auth.user.domain.Status
import com.gorogoro.auth.user.domain.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class AuthService(
    private val loadUserPort: LoadUserPort,
    private val checkUserPort: CheckUserPort,
    private val saveUserPort: SaveUserPort,
    private val refreshTokenPort: RefreshTokenPort,
    private val jwtProvider: JwtProvider,
    private val passwordEncoder: PasswordEncoder,
    private val nicknameGenerator: NicknameGenerator,
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
            role = cmd.role
        )
        saveUserPort.saveUser(newUser)
    }

    @Transactional
    override fun login(cmd: LoginCommand): TokenResponse {
        val user = loadUserPort.findByEmail(cmd.email)
            ?: throw BusinessException.builder(ErrorCode.USER_NOT_FOUND).build()

        if (!passwordEncoder.matches(cmd.password, user.passwordEncrypted)) {
            throw BusinessException.builder(ErrorCode.INVALID_PASSWORD).build()
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

        return TokenResponse(accessToken, refreshToken)
    }

    @Transactional(noRollbackFor = [BusinessException::class])
    override fun refresh(cmd: RefreshTokenCommand): AccessTokenResponse {
        val savedRefreshToken = refreshTokenPort.findByRefreshToken(cmd.refreshToken)
            ?: throw BusinessException.builder(ErrorCode.REFRESH_TOKEN_NOT_FOUND).build()

        if(savedRefreshToken.isExpired()){
            deleteRefreshToken(savedRefreshToken.id)
            throw BusinessException.builder(ErrorCode.TOKEN_EXPIRED).build()
        }

        val user = loadUserPort.findById(savedRefreshToken.userId)
                ?: throw BusinessException.builder(ErrorCode.USER_NOT_FOUND).build()

        if(user.status != Status.ACTIVATED) {
            deleteRefreshToken(savedRefreshToken.id)
            throw BusinessException.builder(ErrorCode.USER_STATUS_IS_NOT_VALID).build()
        }

        val accessToken = jwtProvider.createAccessToken(user.id, user.role)
        savedRefreshToken.changeToken(accessToken, Instant.now().plusSeconds(3155760000))
        refreshTokenPort.save(savedRefreshToken)
        return AccessTokenResponse(savedRefreshToken.refreshToken)
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

        } while (checkUserPort.existsByNickname(nickname))

        return nickname
    }

    private fun deleteRefreshToken(id: Long){
        refreshTokenPort.deleteRefreshTokenById(id)
    }
}
