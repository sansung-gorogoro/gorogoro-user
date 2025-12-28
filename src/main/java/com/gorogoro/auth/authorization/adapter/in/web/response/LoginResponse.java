package com.gorogoro.auth.authorization.adapter.in.web.response;

import com.gorogoro.auth.authorization.application.dto.result.LoginResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponse {
    private Long userId;
    private String name;
    private String email;
    private String accessToken;

    public static LoginResponse from(LoginResult loginResult) {
        return LoginResponse.builder()
                .userId(loginResult.getUserId())
                .name(loginResult.getName())
                .email(loginResult.getEmail())
                .accessToken(loginResult.getAccessToken())
                .build();
    }
}

