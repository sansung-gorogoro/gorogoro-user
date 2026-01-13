package com.gorogoro.auth.user.domain.model;

import com.gorogoro.auth.global.exception.BaseException;
import com.gorogoro.auth.global.exception.code.UserErrorCode;
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
    private static final Pattern NAME_REGEX = Pattern.compile("^[가-힣]+$");

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

    public void updateProfile(String name) {
        validateName(name);
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new BaseException(UserErrorCode.STRING_CANT_BE_BLANK);
        }
        if (!NAME_REGEX.matcher(name).matches()) {
            throw new BaseException(UserErrorCode.COMPETE_KOREAN_NAME);
        }
        if (name.length() > NAME_MAX_LENGTH) {
            throw new BaseException(UserErrorCode.STRING_LENGTH_TOO_MUCH);
        }
    }

    public void updatePassword(String encryptedPassword) {
        this.password = encryptedPassword;
        this.updatedAt = LocalDateTime.now();
    }
}

