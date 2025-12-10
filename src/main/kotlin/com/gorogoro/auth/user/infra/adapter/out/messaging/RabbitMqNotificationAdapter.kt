package com.gorogoro.auth.user.infra.adapter.out.messaging

import com.gorogoro.auth.user.application.port.out.SendNotificationPort
import com.gorogoro.auth.user.infra.adapter.out.messaging.config.EmailType
import com.gorogoro.auth.user.infra.adapter.out.messaging.config.RabbitConfig
import com.gorogoro.auth.user.infra.adapter.out.messaging.dto.NotificationEvent
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class RabbitMqNotificationAdapter(
    private val rabbitTemplate: RabbitTemplate
) : SendNotificationPort {

    override fun sendWelcomeNotification(email: String, username: String) {
        val event = NotificationEvent(
            email = email,
            type = EmailType.WELCOME,
            payload = mapOf("username" to username)
        )

        rabbitTemplate.convertAndSend(
            RabbitConfig.NOTIFICATION_EXCHANGE,
            RabbitConfig.NOTIFICATION_ROUTING_KEY,
            event
        )
    }

    override fun sendVerificationEmail(email: String): String? {
        val event = NotificationEvent(
            email = email,
            type = EmailType.EMAIL_VERIFICATION
        )

        val response = rabbitTemplate.convertSendAndReceive(
            RabbitConfig.NOTIFICATION_EXCHANGE,
            RabbitConfig.NOTIFICATION_ROUTING_KEY,
            event
        )

        return response?.toString()
    }
}