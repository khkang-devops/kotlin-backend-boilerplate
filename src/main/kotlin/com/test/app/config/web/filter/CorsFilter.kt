package com.test.app.config.web.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class CorsFilter : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        val origin = request.getHeader("Origin")
        if (origin.isNullOrBlank()) {
            response.setHeader("Access-Control-Allow-Origin", "*")
        } else {
            response.setHeader("Access-Control-Allow-Credentials", "true")
            response.setHeader("Access-Control-Allow-Origin", origin)
        }

        response.setHeader("Access-Control-Allow-Methods", "*")
        response.setHeader("Access-Control-Max-Age", "3600")
        response.setHeader("Access-Control-Allow-Headers", "*")

        if ("OPTIONS".equals(request.method, ignoreCase = true)) {
            // OPTIONS 요청인 경우는 바로 응답.
            response.status = HttpServletResponse.SC_OK
        } else {
            filterChain.doFilter(request, response)
        }
    }
}