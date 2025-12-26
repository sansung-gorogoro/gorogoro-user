package com.gorogoro.auth.user.application.service;

import com.gorogoro.auth.global.exception.BaseException;
import com.gorogoro.auth.global.exception.code.UserErrorCode;
import com.gorogoro.auth.user.application.dto.command.RegisterCommand;
import com.gorogoro.auth.user.application.dto.result.RegisterResult;
import com.gorogoro.auth.user.application.port.in.RegisterUseCase;
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
public class UserService implements RegisterUseCase {
    private final UserQueryPort userQueryPort;
    private final UserCommandPort userCommandPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public RegisterResult register(RegisterCommand request) {
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
        return RegisterResult.from(savedUser);
    }
}
