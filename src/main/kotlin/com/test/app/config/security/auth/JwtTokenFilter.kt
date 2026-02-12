package com.test.app.config.security.auth

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

private const val AUTHORIZATION = "Authorization"
private const val BEARER = "Bearer"

class JwtTokenFilter(
    private val jwtTokenProvider: JwtTokenProvider,
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        extractBearerToken(request)?.let {
            if (jwtTokenProvider.validateAccessToken(request, it)) {
                jwtTokenProvider.getAuthentication(request, it)?.let { authentication ->
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun extractBearerToken(request: HttpServletRequest): String? =
        request.getHeader(AUTHORIZATION)?.let {
            val (tokenType, token) = splitToTokenFormat(it)
            if (tokenType == BEARER) {
                token
            } else {
                null
            }
        }

    private fun splitToTokenFormat(authorization: String): Pair<String, String> =
        try {
            val tokenFormat = authorization.split(" ")
            tokenFormat[0] to tokenFormat[1]

        } catch (e: Exception) {
            logger.warn("invalid format of Authorization header: $authorization")
            Pair("", "")
        }
    }
