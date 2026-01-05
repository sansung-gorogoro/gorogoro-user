package com.gorogoro.auth.user.domain.model;

import com.gorogoro.auth.user.domain.type.UserRole;
import com.gorogoro.auth.user.domain.type.UserStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private Long id;
    private String email;
    private String name;
    private String password;
    private UserRole role;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private static final int NAME_MAX_LENGTH = 7;

    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$");

    private static final Pattern NAME_REGEX =
            Pattern.compile("^[가-힣]+$");

    private static final Pattern PASSWORD_POLICY_REGEX =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");

    public static User createUser(String email, String name, String password, UserRole role, UserStatus status) {
        return User.builder()
                .email(email)
                .name(name)
                .password(password)
                .role(role)
                .status(status)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}

