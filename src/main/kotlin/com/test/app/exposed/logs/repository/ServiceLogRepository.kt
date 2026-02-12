package com.test.app.exposed.logs.repository

import com.test.app.api.common.dto.ServiceLogRequestDto
import com.test.app.exposed.logs.dto.ServiceLogDto
import com.test.app.exposed.logs.table.ServiceLogTable
import org.jetbrains.exposed.v1.core.Op
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.like
import org.jetbrains.exposed.v1.jdbc.Query
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.springframework.stereotype.Repository

@Repository
class ServiceLogRepository {
    private val table = ServiceLogTable

    // 서비스로그목록조회
    fun getServiceLogList(req: ServiceLogRequestDto): Query {
        return table.selectAll()
            .where { getCondition(req) }
            .orderBy(table.seq to SortOrder.DESC)
    }

    // 조회조건
    fun getCondition(request: ServiceLogRequestDto): Op<Boolean> {
        var condition: Op<Boolean> = Op.TRUE

        if (request.userId != null) {
            condition = condition and (table.userId eq request.userId)
        }

        if (request.eventId != null) {
            condition = condition and (table.eventId eq request.eventId)
        }

        if (request.eventDesc != null) {
            condition = condition and (table.eventDesc like "%${request.eventDesc}%")
        }

        return condition
    }

    // 서비스로그등록
    fun insert(logsDto: ServiceLogDto.Command) {
        table.insert {
            it[userId] = logsDto.userId
            it[eventId] = logsDto.eventId
            it[eventDesc] = logsDto.eventDesc
            it[event] = logsDto.event
        }
    }
}