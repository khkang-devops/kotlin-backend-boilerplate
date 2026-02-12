package com.test.app.config.security.auth

import com.test.app.common.code.ResultCode
import com.test.app.common.exception.UnauthorizedException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationProvider() : AuthenticationProvider {
    private val logger = KotlinLogging.logger {}

    fun mockUserAuthenticate(id: String, password: String): Authentication {
        if (!MockUser.isValid(id, password)) {
            throw UnauthorizedException(ResultCode.INVALID_USER_PASSWORD)
        }

        val authorities = MockUser.getRoles(id).map {
            SimpleGrantedAuthority(it.code)
        }.toSet()

        val user = UserPrincipal(
            username = id,
            authorities = authorities,
            userDesc = id,
            partnerName = MockUser.getPartnerName(id)
        )

        return UsernamePasswordAuthenticationToken(user, null, authorities)
    }

    override fun authenticate(authentication: Authentication): Authentication {
        // auth
        val auth = setOf(SimpleGrantedAuthority("primeum"))

        // user info
        val user = UserPrincipal(
            username = "",
            authorities = auth,
            switchUsername = "",
            userDesc = "",
            partnerName = "",
            deptNm = "",
            biztps = listOf(),
            groups = listOf(),
        )

        // return
        return UsernamePasswordAuthenticationToken(user, null, auth)
    }

    override fun supports(authentication: Class<*>): Boolean =
        authentication.isAssignableFrom(UsernamePasswordAuthenticationToken::class.java)
}
