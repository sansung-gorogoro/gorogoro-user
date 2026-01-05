package com.gorogoro.auth.user.application.dto.command;

import com.gorogoro.auth.authorization.adapter.in.web.response.LoginResponse;
import com.gorogoro.auth.user.adapter.in.web.request.RegisterRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterCommand {
    private String email;
    private String name;
    private String password;

    public static RegisterCommand toCommand(RegisterRequest request) {
        return RegisterCommand.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(request.getPassword())
                .build();
    }
}

