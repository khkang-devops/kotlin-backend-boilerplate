package com.test.app.config.security.auth

import com.test.app.common.code.ResultCode
import com.test.app.common.exception.UnauthorizedException
import com.test.app.common.properties.JwtProperties
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.*

const val AUTH_RESULT_CODE = "AUTH_RESULT_CODE"

private const val AUTHORITIES_KEY = "auth"
private const val PARTNER_NAME = "pn"
private const val SWITCH_USER_NAME = "sw"
private const val USER_GROUPS = "ug"

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
) {
    private val logger = KotlinLogging.logger {}

    fun createTokens(
        subject: String,
        audience: String,
        authorities: Set<GrantedAuthority> = setOf(),
        partnerName: String = "",
        groups: List<String>? = listOf()
    ): JwtTokens {
        val authoritiesValue = authorities.joinToString(",") { it.authority }
        val accessToken = createAccessToken(subject, audience, authoritiesValue, partnerName, "", groups)
        val refreshToken = createRefreshToken()
        return JwtTokens(accessToken, refreshToken)
    }

    fun createAccessToken(
        username: String,
        userDesc: String,
        roles: String,
        partnerName: String,
        switchUsername: String? = "",
        groups: List<String>? = listOf()
    ): String {
        val claims = Jwts.claims().setSubject(username).setAudience(userDesc)
        val now = Date()
        val expiration = Date(now.time + jwtProperties.accessTokenValidity.toMillis())

        return Jwts.builder()
            .setClaims(claims)
            .claim(AUTHORITIES_KEY, roles)
            .claim(PARTNER_NAME, partnerName)
            .claim(SWITCH_USER_NAME, switchUsername)
            .claim(USER_GROUPS, groups)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(jwtProperties.getSecretKey(), jwtProperties.getSignatureAlgorithm())
            .compact()
    }

    fun createRefreshToken(): String {
        val expiration = Date(Date().time + jwtProperties.refreshTokenValidity.toMillis())

        return Jwts.builder()
            .setExpiration(expiration)
            .signWith(jwtProperties.getSecretKey(), jwtProperties.getSignatureAlgorithm())
            .compact()
    }

    fun getAuthentication(request: HttpServletRequest, accessToken: String): Authentication? {
        val claims = parseClaims(accessToken)

        if (claims[AUTHORITIES_KEY] == null) {
            logger.error { "권한 정보가 없는 토큰, accessToken: $accessToken, claims: $claims" }
            request.setAttribute(AUTH_RESULT_CODE, ResultCode.INVALID_ACCESS_TOKEN)
            return null
        }

        val authorities = claims[AUTHORITIES_KEY].toString().split(",")
            .map { SimpleGrantedAuthority(it) }
            .toSet()

        val sourceIp = request.getHeader("X-FORWARDED-FOR") ?: request.remoteAddr

        val switchUsername = if (claims[SWITCH_USER_NAME] == null) "" else claims[SWITCH_USER_NAME].toString()
        val groups = if (claims[USER_GROUPS] == null) listOf() else claims[USER_GROUPS] as List<String>

        val user = UserPrincipal(
            username = claims.subject,
            userDesc = claims.audience,
            uri = request.requestURI.orEmpty(),
            sourceIp = sourceIp,
            authorities = authorities,
            partnerName = claims[PARTNER_NAME].toString(),
            switchUsername = switchUsername,
            groups = groups,
        )

        return UsernamePasswordAuthenticationToken(user, null, authorities)
    }

    fun getSubject(accessToken: String): String = parseClaims(accessToken).subject

    fun validateAccessToken(request: HttpServletRequest, accessToken: String): Boolean {
        if (isTokenExpired(accessToken)) {
            request.setAttribute(AUTH_RESULT_CODE, ResultCode.EXPIRED_ACCESS_TOKEN)
            return false
        }

        if (!validateToken(accessToken)) {
            request.setAttribute(AUTH_RESULT_CODE, ResultCode.INVALID_ACCESS_TOKEN)
            return false
        }

        return true
    }

    fun isTokenExpired(token: String): Boolean = parseClaims(token).expiration.before(Date())

    fun validateToken(token: String): Boolean =
        try {
            parseClaimsJws(token)
            true
        } catch (e: Exception) {
            logger.error(e) { "token: $token" }
            false
        }

    private fun parseClaims(accessToken: String): Claims =
        try {
            parseClaimsJws(accessToken).body
        } catch (e: ExpiredJwtException) {
            logger.warn(e) { "accessToken: $accessToken, claims: ${e.claims}" }
            e.claims
        } catch (e: Exception) {
            logger.error(e) { "accessToken: $accessToken" }
            throw UnauthorizedException(ResultCode.INVALID_ACCESS_TOKEN)
        }

    private fun parseClaimsJws(token: String): Jws<Claims> =
        Jwts.parserBuilder()
            .setSigningKey(jwtProperties.getSecretKey())
            .build()
            .parseClaimsJws(token)
}

data class JwtTokens(
    val accessToken: String,
    val refreshToken: String,
)
