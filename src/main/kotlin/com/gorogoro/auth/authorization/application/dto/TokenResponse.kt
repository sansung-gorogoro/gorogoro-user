package com.gorogoro.auth.authorization.application.dto

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)

data class AccessTokenResponse(
    val accessToken: String,
)
