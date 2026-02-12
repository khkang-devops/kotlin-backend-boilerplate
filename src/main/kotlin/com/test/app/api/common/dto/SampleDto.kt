package com.test.app.api.common.dto

import com.test.app.exposed.logs.table.ServiceLogTable
import org.jetbrains.exposed.v1.core.ResultRow
import java.time.LocalDateTime

data class SampleRequestDto(
    val biztpCd: String,
    val startDate: String,
    val endDate: String,
    val gcodeCds: List<String>,
    val mcodeCds: List<String>,
    val dcodeCds: List<String>,
    val wonUnit: String = "억",
)

data class SampleResponseDto(
    val biztpCd: String,
    val mcodeNm: String,
    val dcodeNm: String,
    val prdtCd: String,
    val prdtNm: String,
)

data class ServiceLogRequestDto(
    val userId: String? = null,
    val eventId: String? = null,
    val eventDesc: String? = null,
    val event: String? = null,
    val logDttm: LocalDateTime? = null,
)

data class ServiceLogResponseDto(
    val userId: String,
    val eventId: String,
    val eventDesc: String,
    val event: String,
    val logDttm: LocalDateTime,
) {
    constructor(row: ResultRow): this(
        userId = row[ServiceLogTable.userId],
        eventId = row[ServiceLogTable.eventId],
        eventDesc = row[ServiceLogTable.eventDesc],
        event = row[ServiceLogTable.event],
        logDttm = row[ServiceLogTable.logDttm],
    )
}