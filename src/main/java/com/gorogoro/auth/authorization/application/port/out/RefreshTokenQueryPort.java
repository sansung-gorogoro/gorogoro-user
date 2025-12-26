package com.gorogoro.auth.authorization.application.port.out;

import com.gorogoro.auth.authorization.domain.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenQueryPort {
    Optional<RefreshToken> getRefreshToken(String token);
}

