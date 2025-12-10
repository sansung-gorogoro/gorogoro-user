package com.gorogoro.auth.user.infra.adapter.out.messaging.consumer.dto

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.gorogoro.auth.user.infra.adapter.out.messaging.consumer.dto.EmailType

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "eventType"
)@JsonSubTypes(
    JsonSubTypes.Type(value = NotificationEvent::class, name = "EMAIL"),
    JsonSubTypes.Type(value = EmailType::class, name = "EMAIL"),
)
sealed interface Event
