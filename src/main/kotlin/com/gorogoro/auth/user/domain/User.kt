package com.gorogoro.auth.user.domain

import com.gorogoro.auth.global.exception.BusinessException
import com.gorogoro.auth.global.exception.ErrorCode
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.Instant

@Entity
@Table(name = "users")
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

    @Column(updatable = false)
    var createdAt: Instant = Instant.now()
        protected set

    var modifiedAt: Instant = Instant.now()
        protected set

    var deletedAt: Instant? = null
        protected set

    var lastLoginAt: Instant? = null
        protected set

    fun updateNickname(newNickname: String) {
        if(newNickname.isBlank()){
            throw BusinessException.builder(ErrorCode.INVALID_NICKNAME).build()
        }
        this.nickname = newNickname
    }

    fun updateUserDate(){
        this.modifiedAt = Instant.now()
    }

    fun lastLogin(now : Instant) {
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
    fun getAuthorities():Collection<GrantedAuthority>{
        return listOf(SimpleGrantedAuthority("ROLE_${this.role}"))
    }
}
