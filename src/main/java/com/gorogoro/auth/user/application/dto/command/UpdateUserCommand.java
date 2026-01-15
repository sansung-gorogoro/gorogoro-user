package com.gorogoro.auth.user.application.dto.command;

import com.gorogoro.auth.user.adapter.in.web.request.UpdateUserRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.AccessLevel;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateUserCommand {
    private Long userId;
    private String name;
    private String password;

    public static UpdateUserCommand of(Long userId, UpdateUserRequest request) {
        return UpdateUserCommand.builder()
                .userId(userId)
                .name(request.getName())
                .password(request.getNewPassword())
                .build();
    }
}
