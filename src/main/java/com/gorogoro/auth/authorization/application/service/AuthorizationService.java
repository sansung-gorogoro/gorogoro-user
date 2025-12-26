package com.gorogoro.auth.authorization.application.service;

import com.gorogoro.auth.authorization.application.dto.command.LoginCommand;
import com.gorogoro.auth.authorization.application.dto.result.LoginResult;
import com.gorogoro.auth.authorization.application.port.in.LoginUseCase;
import com.gorogoro.auth.authorization.application.port.out.RefreshTokenCommandPort;
import com.gorogoro.auth.authorization.domain.model.RefreshToken;
import com.gorogoro.auth.global.exception.BaseException;
import com.gorogoro.auth.global.exception.code.AuthErrorCode;
import com.gorogoro.auth.global.exception.code.UserErrorCode;
import com.gorogoro.auth.global.jwt.JwtConstants;
import com.gorogoro.auth.global.jwt.JwtProvider;
import com.gorogoro.auth.user.application.port.out.UserQueryPort;
import com.gorogoro.auth.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements LoginUseCase {
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserQueryPort userQueryPort;
    private final RefreshTokenCommandPort refreshTokenCommandPort;

    @Override
    @Transactional
    public LoginResult login(LoginCommand command) {
        User user = userQueryPort.getUserByEmail(command.getEmail())
                .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

        // 비밀번호 검증
        if (!passwordEncoder.matches(command.getPassword(), user.getPassword())) {
            throw new BaseException(AuthErrorCode.INVALID_CREDENTIALS);
        }

        // JWT 토큰 생성
        String accessToken = jwtProvider.generateAccessToken(user.getId(), String.valueOf(user.getRole()));
        String refreshTokenValue = jwtProvider.generateRefreshToken(user.getId(), String.valueOf(user.getRole()));

        // 리프레시 토큰 저장
        RefreshToken refreshToken = RefreshToken.createRefreshToken(
                refreshTokenValue,
                user.getId(),
                JwtConstants.REFRESH_TOKEN_EXPIRATION_MILLIS
        );

        refreshTokenCommandPort.saveRefreshToken(refreshToken);
        return LoginResult.of(accessToken, refreshTokenValue, user);
    }
}

