package com.test.app.common.exception

import com.test.app.common.code.ResultCode
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.NoHandlerFoundException
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.UnexpectedTypeException


@ControllerAdvice
class GlobalExceptionAdvice {

    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(
        MethodArgumentNotValidException::class,
        UnexpectedTypeException::class,
        HttpMessageNotReadableException::class,
        ServletRequestBindingException::class
    )
    fun handleBadRequestException(request: HttpServletRequest, ex: Exception): ResponseEntity<ErrorResponse> {
        return responseEntityInternal(request, HttpStatus.BAD_REQUEST, ex)
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNotFoundException(request: HttpServletRequest, ex: Exception): ResponseEntity<ErrorResponse> {
        return responseEntityInternal(request, HttpStatus.NOT_FOUND, ex)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleMethodNotAllowedException(request: HttpServletRequest, ex: Exception): ResponseEntity<ErrorResponse> {
        return responseEntityInternal(request, HttpStatus.METHOD_NOT_ALLOWED, ex)
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun handleUnsupportedMediaTypeException(request: HttpServletRequest, ex: Exception): ResponseEntity<ErrorResponse> {
        return responseEntityInternal(request, HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex)
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedException(request: HttpServletRequest, ex: Exception): ResponseEntity<ErrorResponse> {
        return responseEntityInternal(request, HttpStatus.UNAUTHORIZED, ex)
    }

    // BizException 은 200 OK 로 응답 한다.
    @ExceptionHandler(BizException::class)
    fun handleBizException(request: HttpServletRequest, ex: Exception): ResponseEntity<ErrorResponse> {
        return responseEntityInternal(request, HttpStatus.OK, ex)
    }

    @ExceptionHandler(BaseNotFoundEntityException::class)
    fun handleBaseNotFoundEntityException(request: HttpServletRequest, ex: Exception): ResponseEntity<ErrorResponse> {
        return responseEntityInternal(request, HttpStatus.BAD_REQUEST, ex)
    }

    @ExceptionHandler(UnexpectedDataException::class)
    fun handleUnexpectedDataException(request: HttpServletRequest, ex: Exception): ResponseEntity<ErrorResponse> {
        return responseEntityInternal(request, HttpStatus.INTERNAL_SERVER_ERROR, ex)
    }

    @ExceptionHandler(InsightMateException::class)
    fun handleInsightMateException(request: HttpServletRequest, ex: Exception): ResponseEntity<ErrorResponse> {
        return responseEntityInternal(request, HttpStatus.INTERNAL_SERVER_ERROR, ex)
    }

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(request: HttpServletRequest, ex: Exception): ResponseEntity<ErrorResponse> {
        return responseEntityInternal(request, HttpStatus.INTERNAL_SERVER_ERROR, ex)
    }

    private fun responseEntityInternal(
        request: HttpServletRequest,
        status: HttpStatus,
        ex: Exception
    ): ResponseEntity<ErrorResponse> {

        val error = ErrorResponse.error(ex)

        val uri = request.requestURI.orEmpty()
        val errorMsg = "uri=$uri, status=$status, code=${error.code}, message=${error.message}"

        when (status) {
            HttpStatus.INTERNAL_SERVER_ERROR -> logger.error(ex) { errorMsg }
            else -> logger.warn(ex) { errorMsg }
        }

        return ResponseEntity
            .status(status)
            .body(error)
    }
}

data class ErrorResponse(
    val code: String = ResultCode.FAIL.code,
    val message: String = ResultCode.FAIL.message,
) {

    companion object {

        fun error(ex: Exception) : ErrorResponse = when (ex) {
            is MethodArgumentNotValidException -> {
                val message = ex.bindingResult.fieldErrors.joinToString(", ") {
                    "${it.field}: ${it.defaultMessage.orEmpty()}"
                }
                ErrorResponse(message = message)
            }
            is HttpMessageNotReadableException -> {
                when (val cause = ex.cause) {
                    is InvalidFormatException ->
                        ErrorResponse(
                            code = ResultCode.INVALID_PARAMETER.code,
                            message = "${cause.path.first().fieldName.orEmpty()}: 올바른 형식이여야 합니다."
                        )
                    is MismatchedInputException ->
                        ErrorResponse(
                            code = ResultCode.INVALID_PARAMETER.code,
                            message = "${cause.pathReference.orEmpty()}: 널이여서는 안됩니다."
                        )
                    is ValueInstantiationException ->
                        ErrorResponse(
                            code = ResultCode.INVALID_PARAMETER.code,
                            message = cause.cause?.message ?: cause.message.orEmpty()
                        )
                    else -> {
                        val msg = cause?.message ?: ex.message.orEmpty()
                        ErrorResponse(message = msg)
                    }
                }
            }
            is UnauthorizedException -> ErrorResponse(ex.code(), ex.message())
            is InsightMateException -> ErrorResponse(ex.code(), ex.message())
            is BizException -> ErrorResponse(ex.code(), ex.message())
            is BaseNotFoundEntityException -> ErrorResponse(ex.code, ex.message)
            is UnexpectedDataException -> ErrorResponse(ex.code, ex.message)
            else ->  ErrorResponse()
            // 보안 조치 (AppScan): 디버깅, 예외 노출하면 안됨.
//            else ->  ErrorResponse(message = ex.message.orEmpty().take(200).replace("\n", ""))
        }
    }
}
