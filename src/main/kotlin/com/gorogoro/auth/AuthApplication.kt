package com.gorogoro.auth

import com.gorogoro.auth.user.infra.adapter.out.messaging.config.MessagingProps
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(MessagingProps::class)
class AuthApplication

fun main(args: Array<String>) {
	runApplication<AuthApplication>(*args)
}
