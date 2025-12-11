package com.gorogoro.auth.user.infra.adapter.out.messaging.consumer.dto

data class NotificationEvent(
    val email: String,
    val type: EmailType,
    val payload: Map<String, Any> = emptyMap()
): Event