package com.gorogoro.auth.user.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {
    ADMIN("관리자"),
    USER("사용자"),
    INSTRUCTOR("강사");

    @Getter
    private final String description;
}

