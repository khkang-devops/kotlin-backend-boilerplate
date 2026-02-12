package com.test.app.api.user.web

import com.test.app.api.common.ApiResponse
import com.test.app.api.logs.service.LogsService
import com.test.app.api.user.dto.AccessTokenRequest
import com.test.app.api.user.dto.LoginRequest
import com.test.app.api.user.dto.LoginResponse
import com.test.app.api.user.service.AuthUserService
import com.test.app.common.code.ResultCode
import com.test.app.common.exception.UnauthorizedException
import com.test.app.common.support.sha256
import com.test.app.config.swagger.DisableSwaggerSecurity
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RestController
class AuthUserController(
    private val authUserService: AuthUserService,
    private val logsService: LogsService,
) {
    @DisableSwaggerSecurity
    @Operation(summary = "사용자 로그인", description = "사용자 로그인 입니다.")
    @PostMapping("/auth/user/login")
    fun login(@RequestBody req: LoginRequest): ApiResponse<LoginResponse> {
        return ApiResponse(data = authUserService.login(req.username, req.password))
    }

    @Operation(summary = "사용자 로그아웃", description = "사용자 로그아웃 입니다.")
    @PostMapping("/auth/user/logout")
    fun logout(): ApiResponse<Void> {
        return ApiResponse(ResultCode.SUCCESS)
    }

    @DisableSwaggerSecurity
    @Operation(summary = "엑세스 토큰 재발급", description = "엑세스 토큰을 재발급 합니다.")
    @PostMapping("/auth/access-token/reissue")
    fun getAccessTokenReissue(
        @RequestHeader("Refresh-Token") refreshToken: String?,
        @RequestBody req: AccessTokenRequest,
        request: HttpServletRequest
    ): ApiResponse<String> {
        val accessToken = authUserService.getAccessTokenReissue(refreshToken ?: "", req)

        if (accessToken.isNullOrEmpty()) {
            throw UnauthorizedException(ResultCode.EXPIRED_ACCESS_TOKEN)
        } else {
            // eventDt
            val dateTime = OffsetDateTime.now(ZoneOffset.ofHours(9))
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
            val eventDt = dateTime.format(formatter)

            // prev_token
            val prevToken = request.getHeader("Authorization")
                ?.replace("Bearer ", "")
                ?.sha256()
                ?: "none"

            // token
            val token = accessToken
                ?.sha256()
                ?: "none"

            // ip
            val ip = request.getHeader("X-FORWARDED-FOR") ?: request.remoteAddr

            // userAgent
            val userAgent = request.getHeader("User-Agent") ?: "Unknown"

            // event
            val event = """{"eventDt": "$eventDt", "user": "$token", "prevUser": "$prevToken", "ip": "$ip", "userAgent": "$userAgent"}"""

            // service log 저장
            logsService.saveLogTokenReissue(req.username, event)

            // return
            return ApiResponse(data=accessToken)
        }
    }
}