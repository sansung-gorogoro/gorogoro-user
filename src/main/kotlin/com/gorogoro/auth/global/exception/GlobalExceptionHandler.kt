package com.gorogoro.auth.global.exception

import com.gorogoro.auth.global.exception.dto.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<ErrorResponse> {

        val response = ErrorResponse(
            message = e.message,
            code = e.CustomErrorCode
        )

        return ResponseEntity.status(e.httpStatus).body(response)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorField = e.bindingResult.fieldErrors.firstOrNull()?.field

        val errorCode = ValidationErrorCode.findByField(errorField) ?: ValidationErrorCode.INVALID_INPUT_IN_COMMAND

        val response = ErrorResponse(
            message = errorCode.message,
            code = errorCode.errorCode
        )

        return ResponseEntity
            .status(errorCode.httpStatus)
            .body(response)
    }


    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        val error = ErrorCode.GLOBAL_ERROR_UNEXPECTED
        val response = ErrorResponse(
            message = error.message,
            code = error.errorCode
        )

        return ResponseEntity.status(error.status).body(response)
    }
}
