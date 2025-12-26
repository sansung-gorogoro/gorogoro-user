package com.gorogoro.auth.authorization.adapter.in.web.response;

import com.gorogoro.auth.authorization.application.dto.result.LoginResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Long userId;
    private String name;
    private String email;
    private String accessToken;
    private String refreshToken;

    public static LoginResponse from(LoginResult loginResult) {
        return LoginResponse.builder()
                .userId(loginResult.getUserId())
                .name(loginResult.getName())
                .email(loginResult.getEmail())
                .accessToken(loginResult.getAccessToken())
                .refreshToken(loginResult.getRefreshToken())
                .build();
    }
}

