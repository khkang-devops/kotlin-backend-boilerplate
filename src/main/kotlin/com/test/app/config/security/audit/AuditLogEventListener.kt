package com.test.app.config.security.audit

import com.test.app.common.support.writeValueAsString
import com.test.app.exposed.logs.repository.UserAuditLogRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class AuditLogEventListener(
    private val userAuditLogRepository: UserAuditLogRepository
) {
    private val logger = KotlinLogging.logger {}

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun insertAuditSqlLog(event: AuditLogSqlEvent) {
        logger.info { "[audit_log] ${event.writeValueAsString()}" }
        // userAuditLogRepository.insert(event.toCommand())
    }

    @Async
    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun insertAuditUriLog(event: AuditLogUriEvent) {
        logger.info { "[audit_log] ${event.writeValueAsString()}" }
        // userAuditLogRepository.insert(event.toCommand())
    }
}
