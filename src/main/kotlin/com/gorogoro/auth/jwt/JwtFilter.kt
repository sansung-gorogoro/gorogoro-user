package com.gorogoro.auth.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.gorogoro.auth.authorization.application.port.out.LoadUserPort
import com.gorogoro.auth.global.exception.ErrorCode
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val jwtProvider: JwtProvider,
    private val loadUserPort: LoadUserPort,
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization")

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val token = header.substring(7)

        try {
            val userId = jwtProvider.getUserIdIfValid(token)
            val user = loadUserPort.findById(userId)

            if (user != null) {
                val customUserDetails = CustomUserDetails(user, user.getAuthorities())
                val authToken = UsernamePasswordAuthenticationToken(
                    customUserDetails, null, customUserDetails.authorities
                )
                SecurityContextHolder.getContext().authentication = authToken
            }

            filterChain.doFilter(request, response)

        } catch (e: ExpiredJwtException) {
            SecurityContextHolder.clearContext()
            setErrorResponse(response, ErrorCode.TOKEN_EXPIRED)
        } catch (e: JwtException) {
            SecurityContextHolder.clearContext()
            setErrorResponse(response, ErrorCode.INVALID_TOKEN_FORMAT)
        } catch (e: Exception) {
            SecurityContextHolder.clearContext()
            setErrorResponse(response, ErrorCode.INVALID_TOKEN_FORMAT)
        }
    }

    private fun setErrorResponse(response: HttpServletResponse, errorCode: ErrorCode) {
        response.status = errorCode.status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"

        val errorResponse = mapOf(
            "code" to errorCode.name,
            "message" to errorCode.message
        )

        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }
}