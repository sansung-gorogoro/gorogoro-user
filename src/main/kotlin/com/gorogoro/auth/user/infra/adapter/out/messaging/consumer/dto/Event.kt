package com.gorogoro.auth.user.infra.adapter.out.messaging.consumer.dto

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)@JsonSubTypes(
    JsonSubTypes.Type(value = EmailType::class, name = "WELCOME"),
    JsonSubTypes.Type(value = EmailType::class, name = "VERIFICATION"),
)
sealed interface Event
