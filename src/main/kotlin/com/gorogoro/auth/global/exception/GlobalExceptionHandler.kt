package com.gorogoro.auth.global.exception

import com.gorogoro.auth.global.exception.dto.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<ErrorResponse> {

        val response = ErrorResponse(
            status = e.httpStatus.value(),
            error = e.httpStatus.reasonPhrase,
            message = e.message,
            errorCode = e.CustomErrorCode
        )

        return ResponseEntity.status(e.httpStatus).body(response)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        val error = ErrorCode.GLOBAL_ERROR_UNEXPECTED
        val response = ErrorResponse(
            status = error.status.value(),
            error = error.status.reasonPhrase,
            message = error.message,
            errorCode = error.errorCode
        )

        return ResponseEntity.status(error.status).body(response)
    }
}
