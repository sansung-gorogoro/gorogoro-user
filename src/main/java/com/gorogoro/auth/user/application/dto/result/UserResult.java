package com.gorogoro.auth.user.application.dto.result;

import com.gorogoro.auth.user.domain.model.User;
import com.gorogoro.auth.user.domain.type.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.AccessLevel;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResult {
    private Long userId;
    private String email;
    private String name;
    private UserRole role;

    public static UserResult from(User user) {
        return UserResult.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }
}
