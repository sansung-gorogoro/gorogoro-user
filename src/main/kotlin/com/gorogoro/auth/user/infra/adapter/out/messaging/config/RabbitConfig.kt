package com.gorogoro.auth.user.infra.adapter.out.messaging.config

import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class RabbitConfig {
    companion object {
        const val NOTIFICATION_EXCHANGE = "poc.spring.exchange"
        const val NOTIFICATION_ROUTING_KEY = "poc.spring.routing"
    }

    @Bean
    fun messageConverter(): MessageConverter = Jackson2JsonMessageConverter()

    @Bean
    fun notificationExchange(): TopicExchange = TopicExchange(NOTIFICATION_EXCHANGE)
}