package com.gorogoro.auth.authorization.application.dto.result;

import com.gorogoro.auth.user.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResult {
    private Long userId;
    private String email;
    private String name;
    private String accessToken;
    private String refreshToken;

    public static LoginResult of(String accessToken, String refreshToken, User user) {
        return LoginResult.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}

