package com.gorogoro.auth.authorization.application.port.out;

import com.gorogoro.auth.authorization.domain.model.RefreshToken;

public interface RefreshTokenCommandPort {
    void saveRefreshToken(RefreshToken refreshToken);
}

