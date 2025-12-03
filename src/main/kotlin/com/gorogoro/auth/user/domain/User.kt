package com.gorogoro.auth.user.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener::class)
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    email: String,
    passwordHashed: String,
    name: String,
    nickname: String,
    role: Role,
    status: Status = Status.ACTIVATED
) {
    @Column(unique = true)
    var email: String = email
        protected set
    var passwordHashed: String = passwordHashed
        protected set
    val name: String = name

    @Column(unique = true)
    var nickname: String = nickname
        protected set

    @Enumerated(EnumType.STRING)
    var role: Role = role
        protected set

    @Enumerated(EnumType.STRING)
    var status: Status = status
        protected set

    @CreatedDate
    @Column(updatable = false)
    var createdAt: LocalDateTime? = null
        protected set

    @LastModifiedDate
    var modifiedAt: LocalDateTime? = null
        protected set

    var deletedAt: LocalDateTime? = null
        protected set

    var lastLoginAt: LocalDateTime? = null
        protected set


    fun updateNickname(newNickname: String) {
        this.nickname = newNickname
    }

    fun lastLogin() {
        this.lastLoginAt = LocalDateTime.now()
    }

    fun delete() {
        this.status = Status.DELETED
        this.deletedAt = LocalDateTime.now()
    }
}