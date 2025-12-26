package com.gorogoro.auth.authorization.adapter.out.persistence.mapper;

import com.gorogoro.auth.authorization.adapter.out.persistence.entity.RefreshTokenRedisEntity;
import com.gorogoro.auth.authorization.domain.model.RefreshToken;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapper {
    public RefreshToken toDomain(RefreshTokenRedisEntity entity) {
        if (entity == null) {
            return null;
        }

        return RefreshToken.builder()
                .refreshToken(entity.getRefreshToken())
                .userId(entity.getUserId())
                .expiresAt(System.currentTimeMillis() + entity.getExpiration())
                .build();
    }

    public RefreshTokenRedisEntity toEntity(RefreshToken domain) {
        if (domain == null) {
            return null;
        }
        long expirationTime = domain.getExpiresAt() - System.currentTimeMillis();

        return RefreshTokenRedisEntity.builder()
                .refreshToken(domain.getRefreshToken())
                .userId(domain.getUserId())
                .expiration(Math.max(expirationTime, 0L))
                .build();
    }
}
