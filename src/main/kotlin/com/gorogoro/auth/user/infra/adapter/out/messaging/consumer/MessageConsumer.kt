package com.gorogoro.auth.user.infra.adapter.out.messaging.consumer

import com.rabbitmq.client.Channel
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class MessageConsumer(
    private val rabbitTemplate: RabbitTemplate
) {
//    @RabbitListener(queues = ["\${rabbit.events.queues.user.name}"], ackMode = "MANUAL")
//    @Throws(IOException::class)
//    fun handle(message: Message, channel: Channel) {
//        val tag = message.messageProperties.deliveryTag
//
//        try {
//            channel.basicAck(tag, false)
//        } catch (ex: Exception) {
//            channel.basicNack(tag, false, false)
//        }
//    }
}
