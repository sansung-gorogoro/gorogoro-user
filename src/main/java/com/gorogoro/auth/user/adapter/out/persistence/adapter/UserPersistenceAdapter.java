package com.gorogoro.auth.user.adapter.out.persistence.adapter;

import com.gorogoro.auth.user.adapter.out.persistence.entity.UserJpaEntity;
import com.gorogoro.auth.user.adapter.out.persistence.mapper.UserMapper;
import com.gorogoro.auth.user.adapter.out.persistence.repository.UserJpaRepository;
import com.gorogoro.auth.user.application.port.out.UserCommandPort;
import com.gorogoro.auth.user.application.port.out.UserQueryPort;
import com.gorogoro.auth.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserQueryPort, UserCommandPort {
    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public User saveUser(User user) {
        UserJpaEntity entity = userMapper.toEntity(user);
        UserJpaEntity savedEntity = userJpaRepository.save(entity);
        return userMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> getUserId(Long userId) {
        return userJpaRepository.findById(userId)
                .map(userMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }
}
