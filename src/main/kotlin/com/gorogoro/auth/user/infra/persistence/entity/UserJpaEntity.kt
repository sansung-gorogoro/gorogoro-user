package com.gorogoro.auth.user.infra.persistence.entity

import com.gorogoro.auth.user.model.User
import com.gorogoro.auth.user.model.constant.Role
import com.gorogoro.auth.user.model.constant.Status
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "users")
class UserJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var passwordEncrypted: String,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, unique = true)
    var nickname: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: Status,

    var lastLoginAt: Instant? = null,

    @Column(nullable = false, updatable = false)
    val createdAt: Instant,

    @Column(nullable = false)
    var modifiedAt: Instant,

    var deletedAt: Instant? = null
) {
    fun update(user: User) {
        this.email = user.email
        this.passwordEncrypted = user.passwordEncrypted
        this.name = user.name
        this.nickname = user.nickname
        this.role = user.role
        this.status = user.status
        this.lastLoginAt = user.lastLoginAt
        this.modifiedAt = user.modifiedAt
        this.deletedAt = user.deletedAt
    }
}

fun UserJpaEntity.toDomain(): User {
    return User(
        id = this.id,
        email = this.email,
        passwordEncrypted = this.passwordEncrypted,
        name = this.name,
        nickname = this.nickname,
        role = this.role,
        status = this.status,
        lastLoginAt = this.lastLoginAt,
        createdAt = this.createdAt,
        modifiedAt = this.modifiedAt,
        deletedAt = this.deletedAt
    )
}

fun User.toEntity(): UserJpaEntity {
    return UserJpaEntity(
        id = this.id,
        email = this.email,
        passwordEncrypted = this.passwordEncrypted,
        name = this.name,
        nickname = this.nickname,
        role = this.role,
        status = this.status,
        lastLoginAt = this.lastLoginAt,
        createdAt = this.createdAt,
        modifiedAt = this.modifiedAt,
        deletedAt = this.deletedAt
    )
}