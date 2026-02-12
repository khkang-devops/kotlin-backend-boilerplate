package com.test.app.config.security.audit

import com.test.app.common.properties.SystemProperties
import com.test.app.config.security.auth.UserPrincipalHolder
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.ApplicationEventPublisher
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Order(1)
@Component
class AuditLogUriFilter(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val systemProperties: SystemProperties,
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        if (systemProperties.audit.log.enable) {
            val auditLogUriEvent = UserPrincipalHolder.getUserPrincipal().let {
                AuditLogUriEvent(
                    accountId = it.username.ifBlank { "NA" },
                    accountName = it.userDesc.ifBlank { "NA" },
                    uri = it.uri.ifBlank { request.requestURI.orEmpty() },
                    sourceIp = it.sourceIp.ifBlank { request.getHeader("X-FORWARDED-FOR") ?: request.remoteAddr },
                )
            }

            val uri = auditLogUriEvent.uri.orEmpty()
            if (uri.startsWith("/auth/") || uri.startsWith("/api/")) {
                if (!uri.startsWith("/api/user/audit")
                    && !uri.startsWith("/api/logs")) {
                    applicationEventPublisher.publishEvent(auditLogUriEvent)
                }
            }
        }

        filterChain.doFilter(request, response)
    }
}
