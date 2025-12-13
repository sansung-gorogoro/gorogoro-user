package com.gorogoro.auth.user.infra.adapter.out.messaging.producer

import com.gorogoro.auth.user.application.port.out.SendNotificationPort
import com.gorogoro.auth.user.infra.adapter.out.messaging.config.MessagingProps
import com.gorogoro.auth.user.infra.adapter.out.messaging.consumer.dto.EventEnvelope
import com.gorogoro.auth.user.infra.adapter.out.messaging.consumer.dto.SendGreetingMailEvent
import com.gorogoro.auth.user.infra.adapter.out.messaging.producer.constant.EventType
import org.springframework.amqp.core.MessageDeliveryMode
import org.springframework.amqp.core.MessageProperties
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import java.util.Date


@Service
class MessageProducer(
    private val rabbitTemplate: RabbitTemplate,
    private val messagingProps: MessagingProps
): SendNotificationPort {

    override fun sendWelcomeNotification(email: String, username: String) {
        val event = SendGreetingMailEvent.of(
            email,
            username,
        )
        val eventPayload = EventEnvelope.wrap(event)

        val eventType = EventType.CREATED.eventType
        rabbitTemplate.convertAndSend(
            messagingProps.exchange,
            messagingProps.toRoutingKey(eventType),
            eventPayload
        ){
                msg ->
            msg.messageProperties.setHeader(
                "__TypeId__",
                "com.gorogoro.notification.rabbitmq.domain.dto.EventEnvelope"// null도 가능
            )
            msg.apply {
                messageProperties.messageId = eventPayload.eventId.toString()
                messageProperties.timestamp = Date.from(eventPayload.occurredAt)
                messageProperties.type = eventPayload.type
                messageProperties.contentType = MessageProperties.CONTENT_TYPE_JSON
                messageProperties.deliveryMode = MessageDeliveryMode.PERSISTENT

                eventPayload.metadata.forEach{ (k, v) -> messageProperties.setHeader("meta-" + k, v) }
                }
            msg
        }
    }

    override fun sendVerificationEmail(email: String): String? {
        TODO("Not yet implemented")
    }
}