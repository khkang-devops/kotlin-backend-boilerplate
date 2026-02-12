package com.test.app.config.security.auth

import com.test.app.api.common.ApiResponse
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@RestControllerAdvice
class RefreshTokenAdvice(
    private val jwtTokenProvider: JwtTokenProvider,
) : ResponseBodyAdvice<Any> {
    override fun supports(
        returnType: MethodParameter,
        converterType: Class<out HttpMessageConverter<*>>
    ): Boolean = true

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        mediaType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        // 리프레시토큰신규발급
        val refreshToken = jwtTokenProvider.createRefreshToken()

        // 웅답헤더에추가
        if (body is ApiResponse<*>) {
            response.headers.add("Refresh-Token", refreshToken)
        }
        return body
    }
}