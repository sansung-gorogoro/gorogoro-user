package com.gorogoro.auth.user.application.port.out

interface SendNotificationPort {
    fun sendWelcomeNotification(email: String, username: String)
    fun sendVerificationEmail(email: String): String?
}