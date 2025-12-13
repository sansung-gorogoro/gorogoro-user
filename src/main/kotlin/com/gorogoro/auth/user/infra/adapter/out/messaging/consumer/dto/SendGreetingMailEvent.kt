package com.gorogoro.auth.user.infra.adapter.out.messaging.consumer.dto

@ConsistentCopyVisibility
data class SendGreetingMailEvent private constructor(
    val email: String,
    val user: String,
    override val type: String = "user.greeting.send-email",
) : Event {

    companion object {
        fun of(email: String, username: String): SendGreetingMailEvent {
            return SendGreetingMailEvent(email, username)
        }
    }
}