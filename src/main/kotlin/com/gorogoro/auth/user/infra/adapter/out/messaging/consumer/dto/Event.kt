package com.gorogoro.auth.user.infra.adapter.out.messaging.consumer.dto

interface Event{
    val type: String

    fun version(): String = "v1"
}
