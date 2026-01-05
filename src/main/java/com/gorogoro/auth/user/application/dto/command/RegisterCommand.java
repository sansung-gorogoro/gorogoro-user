package com.gorogoro.auth.user.application.dto.command;

import com.gorogoro.auth.user.adapter.in.web.request.RegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

