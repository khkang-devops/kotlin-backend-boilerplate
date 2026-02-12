package com.test.app.api.logs.dto

import com.test.app.common.code.LogEventCode
import com.test.app.exposed.logs.dto.ServiceLogDto
import com.fasterxml.jackson.annotation.JsonRawValue
import java.time.LocalDateTime

data class LogsDto(
    val logDttm: LocalDateTime = LocalDateTime.now(),
    val userId: String = "",
    val eventId: String = "",
    val eventDesc: String = "",
    // json object 로 하기위해 @JsonRawValue 어노테이션 추가
    @JsonRawValue
    val event: String,
) {
    constructor(userId: String, eventCode: LogEventCode, event: String): this(
        userId = userId,
        eventId = eventCode.code,
        eventDesc = eventCode.desc,
        event = event
    )

    fun toCommand() = ServiceLogDto.Command(
        userId = userId,
        eventId = eventId,
        eventDesc = eventDesc,
        event = event
    )
}
