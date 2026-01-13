package com.gorogoro.auth.user.application.service;

import com.gorogoro.auth.global.exception.BaseException;
import com.gorogoro.auth.global.exception.code.UserErrorCode;
import com.gorogoro.auth.user.application.dto.command.RegisterUserCommand;
import com.gorogoro.auth.user.application.dto.command.UpdateUserCommand;
import com.gorogoro.auth.user.application.dto.result.UserResult;
import com.gorogoro.auth.user.application.port.in.GetUserUseCase;
import com.gorogoro.auth.user.application.port.in.RegisterUserUseCase;
import com.gorogoro.auth.user.application.port.in.UpdateUserUseCase;
import com.gorogoro.auth.user.application.port.out.UserCommandPort;
import com.gorogoro.auth.user.application.port.out.UserQueryPort;
import com.gorogoro.auth.user.domain.model.User;
import com.gorogoro.auth.user.domain.type.UserRole;
import com.gorogoro.auth.user.domain.type.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements RegisterUserUseCase, GetUserUseCase, UpdateUserUseCase {
    private final UserQueryPort userQueryPort;
    private final UserCommandPort userCommandPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResult register(RegisterUserCommand request) {
        if (userQueryPort.existsByEmail(request.getEmail())) {
            throw new BaseException(UserErrorCode.USER_ALREADY_EXISTS);
        }

        User user = User.createUser(
                request.getEmail(),
                request.getName(),
                passwordEncoder.encode(request.getPassword()),
                UserRole.USER,
                UserStatus.ACTIVE
        );

        User savedUser = userCommandPort.saveUser(user);
        return UserResult.from(savedUser);
    }

    @Override
    public UserResult getUser(Long userId) {
        User user = userQueryPort.getUserId(userId)
                .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
        return UserResult.from(user);
    }

    @Override
    @Transactional
    public UserResult updateUser(UpdateUserCommand command) {
        User user = userQueryPort.getUserId(command.getUserId())
                .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

        // 닉네임 변경
        user.updateProfile(command.getName());

        // 비밀번호 암호화 및 변경
        String encodedPassword = passwordEncoder.encode(command.getPassword());
        user.updatePassword(encodedPassword);

        // 변경사항 저장
        User savedUser = userCommandPort.saveUser(user);
        return UserResult.from(savedUser);
    }
}
