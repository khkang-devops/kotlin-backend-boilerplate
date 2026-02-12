package com.test.app.config.web.filter

import com.test.app.config.security.auth.UserPrincipalHolder
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
class MdcFilter : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        MDC.put("user.id", UserPrincipalHolder.getUserPrincipal().username.ifBlank { "NA" })

        filterChain.doFilter(request, response)

        MDC.clear()
    }
}