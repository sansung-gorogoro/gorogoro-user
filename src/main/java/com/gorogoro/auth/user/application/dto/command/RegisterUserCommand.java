package com.gorogoro.auth.user.application.dto.command;

import com.gorogoro.auth.user.adapter.in.web.request.RegisterUserRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterUserCommand {
    private String email;
    private String name;
    private String password;

    public static RegisterUserCommand toCommand(RegisterUserRequest request) {
        return RegisterUserCommand.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(request.getPassword())
                .build();
    }
}

