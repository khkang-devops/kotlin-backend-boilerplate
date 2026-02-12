package com.test.app.api.user.service

import com.test.app.api.user.dto.AccessTokenRequest
import com.test.app.api.user.dto.LoginResponse
import com.test.app.api.user.dto.SwitchUserResponse
import com.test.app.config.security.auth.JwtTokenProvider
import com.test.app.config.security.auth.UserPrincipal
import com.test.app.config.security.auth.UserPrincipalHolder
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthUserService(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    @Transactional
    fun login(username: String, password: String): LoginResponse {
        val authenticationToken = UsernamePasswordAuthenticationToken(username, password)
        val authentication = authenticationManager.authenticate(authenticationToken)
        val userPrincipal = authentication.principal as UserPrincipal
        val tokens = jwtTokenProvider.createTokens(
            subject = username,
            audience = userPrincipal.userDesc,
            authorities = authentication.authorities.toSet(),
            partnerName = userPrincipal.partnerName,
            groups = userPrincipal.groups,
        )

        return LoginResponse(
            username = username,
            userDesc = userPrincipal.userDesc,
            deptNm = userPrincipal.deptNm,
            roles = authentication.authorities.map { it.authority },
            biztps = userPrincipal.biztps,
            passwordReset = false,
            accessToken = tokens.accessToken,
            refreshToken = tokens.refreshToken,
            partnerName = userPrincipal.partnerName,
            groups = userPrincipal.groups,
        )
    }

    fun getAccessTokenReissue(refreshToken: String, req: AccessTokenRequest): String =
        if (jwtTokenProvider.validateToken(refreshToken)) {
            jwtTokenProvider.createAccessToken(
                username = req.username,
                userDesc = req.userDesc,
                roles = req.roles.joinToString(",") { it },
                partnerName = req.partnerName,
                switchUsername = "",
                groups = req.groups,
            )
        } else {
            ""
        }
}