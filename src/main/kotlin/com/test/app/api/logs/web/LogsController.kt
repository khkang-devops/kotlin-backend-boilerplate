package com.test.app.api.logs.web

import com.test.app.api.common.ApiResponse
import com.test.app.api.logs.service.LogsService
import com.test.app.common.code.LogEventCode
import com.test.app.common.code.ResultCode
import com.test.app.common.extension.encode
import com.test.app.common.extension.getHeaderOrDefault
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "로그 수집", description = "서비스 이용 로그 수집 API")
@RestController
class LogsController(
    private val logsService: LogsService
) {
    @Operation(summary = "서비스 로그 전송 (event: json string)")
    @PostMapping("/api/logs/service/{eventId}")
    fun serviceLogs(
        @PathVariable eventId: String,
        @RequestBody event: String,
        request: HttpServletRequest
    ): ApiResponse<Void> {
        // ip
        val ip = request.getHeaderOrDefault(FORWARDED_FOR, request.remoteAddr)

        // userAgent
        val userAgent = request.getHeaderOrDefault(USER_AGENT, "Unknown")

        // token
        val token = request.getHeader(AUTHORIZATION)
            ?.replace("Bearer ", "")
            ?.encode()
            ?: "none"

        // 추가정보
        val addInfo =  """, "user": "$token", "ip": "$ip", "userAgent": "$userAgent""""

        // user, ip, userAgent 추가
        val index = event.lastIndexOf('}')
        val addEvent =
            if (index != -1) {
                event.substring(0, index) + addInfo + event.substring(index)
            } else {
                "$event {$addInfo}"
            }

        logsService.saveLogs(LogEventCode.of(eventId), addEvent)

        return ApiResponse(ResultCode.SUCCESS)
    }

    companion object {
        const val USER_AGENT = "User-Agent"
        const val AUTHORIZATION = "Authorization"
        const val FORWARDED_FOR = "X-FORWARDED-FOR"
    }
}