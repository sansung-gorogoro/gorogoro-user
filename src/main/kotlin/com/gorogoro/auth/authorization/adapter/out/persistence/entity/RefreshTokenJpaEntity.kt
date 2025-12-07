package com.gorogoro.auth.authorization.adapter.out.persistence.entity

import com.gorogoro.auth.authorization.model.RefreshToken
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant


@Entity
@Table(name = "refresh_token")
class RefreshTokenJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    var refreshToken: String,

    @Column(nullable = false)
    var refreshTokenExpire: Instant
) {
   companion object {
       fun from(domain: RefreshToken): RefreshTokenJpaEntity {
           return RefreshTokenJpaEntity(
               id = domain.id,
               userId = domain.userId,
               refreshToken = domain.refreshToken,
               refreshTokenExpire = domain.refreshTokenExpire
           )
       }
   }

   fun toDomain(): RefreshToken {
       return RefreshToken(
           id = this.id,
           userId = this.userId,
           refreshToken = this.refreshToken,
           refreshTokenExpire = this.refreshTokenExpire
       )
   }
}