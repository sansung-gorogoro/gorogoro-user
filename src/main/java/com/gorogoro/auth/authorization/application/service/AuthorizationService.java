package com.gorogoro.auth.authorization.application.service;

import com.gorogoro.auth.authorization.application.dto.command.LoginCommand;
import com.gorogoro.auth.authorization.application.dto.result.LoginResult;
import com.gorogoro.auth.authorization.application.dto.result.ReissueResult;
import com.gorogoro.auth.authorization.application.port.in.LoginUseCase;
import com.gorogoro.auth.authorization.application.port.in.LogoutUseCase;
import com.gorogoro.auth.authorization.application.port.in.ReissueUseCase;
import com.gorogoro.auth.authorization.application.port.out.RefreshTokenCommandPort;
import com.gorogoro.auth.authorization.application.port.out.RefreshTokenQueryPort;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthorizationService implements LoginUseCase, ReissueUseCase, LogoutUseCase {
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserQueryPort userQueryPort;
    private final RefreshTokenQueryPort refreshTokenQueryPort;
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

    @Override
    public ReissueResult reissue(String refreshTokenValue) {
        // Refresh Token 조회
        RefreshToken refreshToken = refreshTokenQueryPort.getRefreshToken(refreshTokenValue)
                .orElseThrow(() -> new BaseException(AuthErrorCode.INVALID_REFRESH_TOKEN));

        if (refreshToken == null) {
            throw new BaseException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        // 사용자 정보 조회
        User user = userQueryPort.getUserId(refreshToken.getUserId())
                .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

        // 새로운 Access Token 생성
        String newAccessToken = jwtProvider.generateAccessToken(user.getId(), String.valueOf(user.getRole()));
        return ReissueResult.of(newAccessToken);
    }

    @Override
    @Transactional
    public void logout(String refreshToken) {
        refreshTokenCommandPort.deleteRefreshToken(refreshToken);
    }
}

