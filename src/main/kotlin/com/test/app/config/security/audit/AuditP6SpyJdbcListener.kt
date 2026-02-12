package com.test.app.config.security.audit

import com.test.app.common.properties.SystemProperties
import com.test.app.config.security.auth.UserPrincipalHolder
import com.p6spy.engine.common.StatementInformation
import com.p6spy.engine.event.SimpleJdbcEventListener
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

/**
 * P6Spy 을 사용하여 jdbc query statement 을 훅킹 하여, Audit Log을 기록 하도록 한다.
 * select 까지 하면 너무 많을것 같아, CUD 만 기록 하도록 한다.
 */
@Component
class AuditP6SpyJdbcListener(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val systemProperties: SystemProperties,
): SimpleJdbcEventListener() {
    override fun onBeforeAnyExecute(statementInformation: StatementInformation) {
        if (systemProperties.audit.log.enable)
            auditLogging(statementInformation.sqlWithValues)
    }

    private fun auditLogging(sql: String) {
        if (sql.startsWith("select", true)) {
            return
        }

        if (sql.contains("tb_user_audit_log", true)
            || sql.contains("tb_service_log", true)) {
            return
        }

        val auditLogSqlEvent = UserPrincipalHolder.getUserPrincipal().let {
            AuditLogSqlEvent(
                accountId = it.username.ifBlank { "NA" },
                accountName = it.userDesc.ifBlank { "NA" },
                uri = it.uri,
                sourceIp = it.sourceIp,
                sql = sql.take(3000),
            )
        }

        applicationEventPublisher.publishEvent(auditLogSqlEvent)
    }
}
