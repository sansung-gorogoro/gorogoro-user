package com.gorogoro.auth.user.infra.adapter.out.messaging.dto

import com.gorogoro.auth.user.infra.adapter.out.messaging.config.EmailType

data class NotificationEvent(
    val email: String,
    val type: EmailType,
    val payload: Map<String, Any> = emptyMap()
)