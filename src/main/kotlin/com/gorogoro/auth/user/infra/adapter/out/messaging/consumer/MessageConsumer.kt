package com.gorogoro.auth.user.infra.adapter.out.messaging.consumer

import com.gorogoro.auth.user.infra.adapter.out.messaging.config.RabbitConfig
import com.gorogoro.auth.user.infra.adapter.out.messaging.consumer.dto.Event
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
    /**
     * @param dto RabbitMqConfig.jsonMessageConverter()에 의해 자동 바인딩됨
     * 성공 -> ACK
     * 실패 -> NACK(requeue=false) -> 브로커가 DLX로 전달 -> DLQ로 이동
     */
    @RabbitListener(queues = [RabbitConfig.QUEUE_NAME], ackMode = "MANUAL")
    @Throws(IOException::class) // Channel 메서드들이 IOException을 던질 수 있음 명시
    fun handle(event: Event, message: Message, channel: Channel) {
        val tag = message.messageProperties.deliveryTag

        try {
            channel.basicAck(tag, false)
        } catch (ex: Exception) {
            // log.error("Error processing message", ex) 필요 시 로깅

            // requeue=false로 NACK -> DLX로 이동 -> DLQ에 도착
            channel.basicNack(tag, false, false)
        }
    }
}