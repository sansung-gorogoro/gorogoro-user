package com.gorogoro.auth.user.infra.adapter.out.messaging.producer

import com.gorogoro.auth.user.application.port.out.SendNotificationPort
import com.gorogoro.auth.user.infra.adapter.out.messaging.config.RabbitConfig
import com.gorogoro.auth.user.infra.adapter.out.messaging.consumer.dto.EmailType
import com.gorogoro.auth.user.infra.adapter.out.messaging.consumer.dto.NotificationEvent
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class MessageProducer(
    private val rabbitTemplate: RabbitTemplate
): SendNotificationPort {

    override fun sendWelcomeNotification(email: String, username: String) {
        val eventPayload = NotificationEvent(
            email,
            EmailType.WELCOME,
            mapOf("username" to username)
        )

        rabbitTemplate.convertAndSend(
            RabbitConfig.EXCHANGE_NAME,
            RabbitConfig.ROUTING_KEY,
            eventPayload
        )
    }

    override fun sendVerificationEmail(email: String): String? {
        TODO("Not yet implemented")
    }
}