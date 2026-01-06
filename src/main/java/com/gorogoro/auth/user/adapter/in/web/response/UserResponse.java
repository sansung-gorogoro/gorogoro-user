package com.gorogoro.auth.user.adapter.in.web.response;

import com.gorogoro.auth.user.application.dto.result.UserResult;
import com.gorogoro.auth.user.domain.type.UserRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {
    private Long userId;
    private String email;
    private String name;
    private UserRole role;

    public static UserResponse from(UserResult result) {
        return UserResponse.builder()
                .userId(result.getUserId())
                .email(result.getEmail())
                .name(result.getName())
                .role(result.getRole())
                .build();
    }
}
