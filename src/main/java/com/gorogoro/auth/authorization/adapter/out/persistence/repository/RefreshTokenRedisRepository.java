package com.gorogoro.auth.authorization.adapter.out.persistence.repository;

import com.gorogoro.auth.authorization.adapter.out.persistence.entity.RefreshTokenRedisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRedisRepository extends CrudRepository<RefreshTokenRedisEntity, String> {
}

