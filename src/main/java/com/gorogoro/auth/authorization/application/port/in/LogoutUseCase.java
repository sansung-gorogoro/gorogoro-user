package com.gorogoro.auth.authorization.application.port.in;

public interface LogoutUseCase {
    void logout(String refreshToken);
}
