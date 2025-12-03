package com.gorogoro.auth.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long = 0,
    userEmail: String,
    userPasswordHashed: String,
    val userName: String,
    nickname: String,
    userRole: String,
    userStatus: String,
    @Column(updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    modifiedAt: LocalDateTime,
    deletedAt: LocalDateTime
) {
    @Column(unique = true)
    var userEmail: String = userEmail
        protected set
    var userPasswordHashed: String = userPasswordHashed
        protected set

    @Column(unique = true)
    var nickname: String = nickname
        protected set
    var userRole: String = userRole
        protected set
    var userStatus: String = userStatus
        protected set
    var modifiedAt: LocalDateTime = modifiedAt
        protected set
    var deletedAt: LocalDateTime = deletedAt
        protected set
}