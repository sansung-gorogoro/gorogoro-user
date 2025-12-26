package com.gorogoro.auth.authorization.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    private String refreshToken;
    private Long userId;
    private Long expiresAt;

    public static RefreshToken createRefreshToken(String refreshToken, Long userId, long durationMillis) {
        return RefreshToken.builder()
                .refreshToken(refreshToken)
                .userId(userId)
                .expiresAt(System.currentTimeMillis() + durationMillis)
                .build();
    }

    public boolean isExpired(long currentTime) {
        return currentTime > expiresAt;
    }
}

