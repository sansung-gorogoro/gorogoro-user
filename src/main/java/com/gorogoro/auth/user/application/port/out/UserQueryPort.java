package com.gorogoro.auth.user.application.port.out;

import com.gorogoro.auth.user.domain.model.User;

import java.util.Optional;

public interface UserQueryPort {
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserId(Long id);
    boolean existsByEmail(String email);
}
