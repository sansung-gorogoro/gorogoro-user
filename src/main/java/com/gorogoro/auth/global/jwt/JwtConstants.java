package com.gorogoro.auth.global.jwt;

import java.time.Duration;

public class JwtConstants {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final int BEARER_PREFIX_LENGTH = BEARER_PREFIX.length();
    public static final long REFRESH_TOKEN_EXPIRATION_MILLIS = Duration.ofDays(7).toMillis();
}

