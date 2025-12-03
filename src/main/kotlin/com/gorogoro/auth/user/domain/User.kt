package com.gorogoro.auth.user.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
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
    passwordEncrypted: String,
    name: String,
    nickname: String,
    role: Role,
    status: Status = Status.ACTIVATED
) {
    @Column(unique = true)
    var email: String = email
        protected set
    var passwordEncrypted: String = passwordEncrypted
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

    fun updateUserDate(){
        this.modifiedAt = LocalDateTime.now()
    }

    fun lastLogin(now : LocalDateTime) {
        this.lastLoginAt = now
    }

    fun changeStatus(status : Status) {
            when (status) {
                Status.ACTIVATED -> {
                    this.status = status
                    updateUserDate()
                }
                Status.DELETED -> {
                    this.status = status
                    updateUserDate()
                }
                Status.DORMANT -> {
                    this.status = status
                    updateUserDate()
                }
            }
    }
}
