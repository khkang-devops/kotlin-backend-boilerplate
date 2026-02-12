package com.test.app.config.security.audit

import com.test.app.exposed.logs.dto.UserAuditLogDto
import java.time.LocalDateTime

data class AuditLogSqlEvent(
    val accountId: String,
    val accountName: String? = null,
    val sourceIp: String? = null,
    val uri: String? = null,
    val sql: String? = null,
    val occurDt: LocalDateTime = LocalDateTime.now()
) {
    fun toCommand() = UserAuditLogDto.Command(
        accountId = accountId,
        accountName = accountName,
        sourceIp = sourceIp,
        uri = uri,
        sql = sql,
        occurDt = occurDt
    )
}

data class AuditLogUriEvent(
    val accountId: String,
    val accountName: String? = null,
    val sourceIp: String? = null,
    val uri: String? = null,
    val occurDt: LocalDateTime = LocalDateTime.now()
) {
    fun toCommand() = UserAuditLogDto.Command(
        accountId = accountId,
        accountName = accountName,
        sourceIp = sourceIp,
        uri = uri,
        occurDt = occurDt
    )
}