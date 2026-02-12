package com.test.app.exposed.logs.table

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.javatime.datetime

object UserAuditLogTable : Table("tb_user_audit_log") {
    private val seq = long("seq").autoIncrement()
    val accountId = varchar("account_id", 50)
    val accountName = varchar("account_name", 50).nullable()
    val reqUri = varchar("req_uri", 1000).nullable()
    val sourceIp = varchar("source_ip", 50).nullable()
    val sqlLog = varchar("sql_log", 1000).nullable()
    val occurDt = datetime("occur_dt").nullable()
    val createDt = datetime("create_dt").nullable()

    override val primaryKey = PrimaryKey(seq)
}