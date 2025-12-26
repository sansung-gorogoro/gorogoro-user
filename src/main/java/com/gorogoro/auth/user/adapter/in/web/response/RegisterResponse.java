package com.gorogoro.auth.user.adapter.in.web.response;

import com.gorogoro.auth.user.application.dto.result.RegisterResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private Long userId;
    private String email;
    private String name;

    public static RegisterResponse from(RegisterResult registerResult) {
        return RegisterResponse.builder()
                .userId(registerResult.getUserId())
                .email(registerResult.getEmail())
                .name(registerResult.getName())
                .build();
    }
}

