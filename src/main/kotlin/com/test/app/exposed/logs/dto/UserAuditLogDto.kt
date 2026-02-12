package com.test.app.exposed.logs.dto

import java.time.LocalDateTime

class UserAuditLogDto {

    data class Command(
        val accountId: String,
        val accountName: String? = null,
        val sourceIp: String? = null,
        val uri: String? = null,
        val sql: String? = null,
        val occurDt: LocalDateTime = LocalDateTime.now()
    )
}