package com.gorogoro.auth.authorization.adapter.out.persistence.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refresh_token", timeToLive = 60 * 60 * 24 * 14)
public class RefreshTokenRedisEntity {
    @Id
    private String refreshToken;

    @Indexed
    private Long userId;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expiration;
}
