package com.test.app.api.logs.service

import com.test.app.api.logs.dto.LogsDto
import com.test.app.common.code.LogEventCode
import com.test.app.common.support.writeValueAsString
import com.test.app.config.security.auth.UserPrincipalHolder
import com.test.app.exposed.logs.repository.ServiceLogRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.json.JSONObject
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LogsService(
    private val serviceLogRepository: ServiceLogRepository
) {
    private val logger = KotlinLogging.logger {}

    @Transactional
    fun saveLogs(eventCode: LogEventCode, event: String) {
        val username = when {
            eventCode.code.contains("comn001") -> "none"
            eventCode.code.contains("comn002") -> if (event != null) JSONObject(event).optString("username", "none") else "none"
            eventCode.code.contains("comn003") -> if (event != null) JSONObject(event).optString("username", "none") else "none"
            eventCode.code.contains("comn004") -> if (event != null) JSONObject(event).optString("username", "none") else "none"
            eventCode.code.contains("comn005") -> UserPrincipalHolder.getUsername() // 로그아웃일때 사용자는 최초로그인사용자로 처리
            else -> UserPrincipalHolder.getUsername()
        }

        val logsDto = LogsDto(username, eventCode, event)

        logger.info { "[service_log] ${logsDto.writeValueAsString()}" }

        serviceLogRepository.insert(logsDto.toCommand())
    }

    @Transactional
    fun saveLogTokenReissue(username: String, event: String) {
        val logsDto = LogsDto(username, LogEventCode.of("comn006_token-reissue_req_clk"), event)
        serviceLogRepository.insert(logsDto.toCommand())
    }
}