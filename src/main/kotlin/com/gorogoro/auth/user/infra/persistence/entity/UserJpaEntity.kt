package com.gorogoro.auth.user.infra.persistence.entity

import com.gorogoro.auth.user.domain.User
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
    email: String,
    passwordEncrypted: String,
    name: String,
    nickname: String,
    role: Role,
    status: Status = Status.ACTIVATED,
    createdAt : Instant,
    modifiedAt : Instant,
    deletedAt : Instant? = null,
    lastLoginAt : Instant? = null
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

    @Column(updatable = false)
    var createdAt: Instant = Instant.now()
        protected set

    var modifiedAt: Instant = Instant.now()
        protected set

    var deletedAt: Instant? = deletedAt
        protected set

    var lastLoginAt: Instant? = lastLoginAt
        protected set

    fun toDomain(): User {
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
            modifiedAt = this.modifiedAt
        )
    }

    fun from(user: User): UserJpaEntity{
        return UserJpaEntity(
            id = user.id,
            email = user.email,
            passwordEncrypted = user.passwordEncrypted,
            name = user.name,
            nickname = user.nickname,
            role = user.role,
            status = user.status,
            createdAt = user.createdAt,
            modifiedAt = user.modifiedAt,
            lastLoginAt = user.lastLoginAt,
            deletedAt = user.deletedAt
        )
    }
}
