package com.gorogoro.auth.user.application.service;

import com.gorogoro.auth.global.exception.BaseException;
import com.gorogoro.auth.global.exception.code.UserErrorCode;
import com.gorogoro.auth.user.application.dto.command.RegisterUserCommand;
import com.gorogoro.auth.user.application.dto.command.UpdateUserCommand;
import com.gorogoro.auth.user.application.dto.result.UserNicknamesResult;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public UserNicknamesResult getUserNicknames(List<Long> userIds) {
        List<Long> distinctUserIds = userIds.stream()
                .distinct()
                .toList();

        List<User> users = userQueryPort.getUsersByIds(distinctUserIds);

        // 3. 조회된 유저 Map 생성 (ID -> Nickname)
        Map<Long, String> foundNicknames = users.stream()
                .collect(Collectors.toMap(User::getId, User::getName));

        // 4. 조회된 ID 목록 추출
        List<Long> foundIds = users.stream()
                .map(User::getId)
                .toList();

        // 5. 누락된 ID 계산 (요청 ID - 조회된 ID)
        List<Long> missingIds = distinctUserIds.stream()
                .filter(id -> !foundIds.contains(id))
                .toList();

        // 6. 결과 반환
        return UserNicknamesResult.of(foundNicknames, missingIds);
    }

    @Override
    @Transactional
    public UserResult updateUser(UpdateUserCommand command) {
        User user = userQueryPort.getUserId(command.getUserId())
                .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

        // 도메인 로직을 통해 정보 수정
        user.updateProfile(command.getName());

        // 변경사항 저장
        User savedUser = userCommandPort.saveUser(user);
        return UserResult.from(savedUser);
    }
}
