package com.test.app.exposed.logs.table

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.javatime.datetime
import java.time.LocalDateTime

object ServiceLogTable : Table("tb_service_log") {
    val seq = long("seq").autoIncrement()
    val userId = varchar("user_id", 20)
    val eventId = varchar("event_id", 100)
    val eventDesc = varchar("event_desc", 100)
    val event = text("event")
    val logDttm = datetime("log_dttm").default(LocalDateTime.now())

    override val primaryKey = PrimaryKey(seq)
}