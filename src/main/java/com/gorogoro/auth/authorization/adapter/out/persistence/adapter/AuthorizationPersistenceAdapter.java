package com.gorogoro.auth.authorization.adapter.out.persistence.adapter;

import com.gorogoro.auth.authorization.adapter.out.persistence.entity.RefreshTokenRedisEntity;
import com.gorogoro.auth.authorization.adapter.out.persistence.mapper.RefreshTokenMapper;
import com.gorogoro.auth.authorization.adapter.out.persistence.repository.RefreshTokenRedisRepository;
import com.gorogoro.auth.authorization.application.port.out.RefreshTokenCommandPort;
import com.gorogoro.auth.authorization.application.port.out.RefreshTokenQueryPort;
import com.gorogoro.auth.authorization.domain.model.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorizationPersistenceAdapter implements RefreshTokenQueryPort, RefreshTokenCommandPort {
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final RefreshTokenMapper refreshTokenMapper;

    @Override
    public void saveRefreshToken(RefreshToken refreshToken) {
        RefreshTokenRedisEntity entity = refreshTokenMapper.toEntity(refreshToken);
        refreshTokenRedisRepository.save(entity);
    }

    @Override
    public Optional<RefreshToken> getRefreshToken(String token) {
        return refreshTokenRedisRepository.findById(token)
                .map(refreshTokenMapper::toDomain);
    }
}

