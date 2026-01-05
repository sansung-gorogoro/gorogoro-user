package com.gorogoro.auth.authorization.application.dto.command;

import com.gorogoro.auth.authorization.adapter.in.web.request.LoginRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginCommand {
    private String email;
    private String password;

    public static LoginCommand toCommand(LoginRequest loginRequest) {
        return LoginCommand.builder()
                .email(loginRequest.getEmail())
                .password(loginRequest.getPassword())
                .build();
    }
}

