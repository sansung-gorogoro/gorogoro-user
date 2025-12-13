package com.gorogoro.auth.user.infra.adapter.out.messaging.producer.constant

enum class EventType(
    val eventType: String
) {
    CREATED("user.created"),
    DELETED("user.deleted")
}