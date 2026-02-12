package com.test.app.exposed.logs.repository

import com.test.app.exposed.logs.table.UserAuditLogTable
import com.test.app.exposed.logs.dto.UserAuditLogDto
import org.jetbrains.exposed.v1.jdbc.insert
import org.springframework.stereotype.Repository

@Repository
class UserAuditLogRepository {

    fun insert(command: UserAuditLogDto.Command) {
        UserAuditLogTable.insert {
            it[accountId] = command.accountId
            it[accountName] = command.accountName
            it[sourceIp] = command.sourceIp
            it[reqUri] = command.uri
            it[occurDt] = command.occurDt
            it[sqlLog] = command.sql
        }
    }
}