package com.deuktemsiru.configuration

import com.deuktemsiru.common.ApiResponse
import com.deuktemsiru.common.UnauthorizedException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.ErrorResponse
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(NoSuchElementException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(e: NoSuchElementException) =
        ApiResponse.error(e.message ?: "리소스를 찾을 수 없습니다.", 404)

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequest(e: IllegalArgumentException) =
        ApiResponse.error(e.message ?: "잘못된 요청입니다.", 400)

    @ExceptionHandler(IllegalStateException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleConflict(e: IllegalStateException) =
        ApiResponse.error(e.message ?: "요청을 처리할 수 없습니다.", 409)

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorized(e: UnauthorizedException) =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("WWW-Authenticate", "Bearer")
            .body(ApiResponse.error(e.message ?: "인증 실패", 401))

    @ExceptionHandler(Exception::class)
    fun handleServerError(e: Exception): ResponseEntity<ApiResponse<Nothing>> {
        // Preserve Spring's HTTP status and mandatory headers (for example Allow).
        if (e is ErrorResponse) {
            return ResponseEntity.status(e.statusCode).headers(e.headers)
                .body(ApiResponse.error(e.body.detail ?: "요청을 처리할 수 없습니다.", e.statusCode.value()))
        }
        if (e is HttpMessageNotReadableException || e is MethodArgumentTypeMismatchException) {
            return ResponseEntity.badRequest().body(ApiResponse.error("요청 값의 형식을 확인해 주세요.", 400))
        }
        log.error("Unhandled exception", e)
        return ResponseEntity.internalServerError().body(ApiResponse.error("서버 오류가 발생했습니다.", 500))
    }
}
