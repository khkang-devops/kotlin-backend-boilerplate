package com.test.app.config.security

import com.test.app.common.code.ResultCode
import com.test.app.common.exception.ErrorResponse
import com.test.app.common.properties.SystemProperties
import com.test.app.common.support.writeValueAsString
import com.test.app.config.security.auth.AUTH_RESULT_CODE
import com.test.app.config.security.auth.JwtAuthenticationProvider
import com.test.app.config.security.auth.JwtTokenFilter
import com.test.app.config.security.auth.JwtTokenProvider
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component


private val logger = KotlinLogging.logger {}

@EnableWebSecurity
@Configuration
class SecurityConfig(
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAuthenticationProvider: JwtAuthenticationProvider,
    private val jwtTokenProvider: JwtTokenProvider,
    private val systemProperties: SystemProperties,
    private val env: Environment,
) {

    @Bean
    @Order(1)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .csrf { csrf -> csrf.disable() }
            .sessionManagement { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests { authorize ->
                authorize.requestMatchers("/static/**").permitAll()
                authorize.requestMatchers("/api/user/logout").permitAll()

                if (systemProperties.security.auth.enable) {
                    authorize.requestMatchers("/api/**").authenticated()
                } else {
                    authorize.requestMatchers("/api/**").permitAll()
                }

                authorize.anyRequest().permitAll()
            }
            .exceptionHandling { exceptionHandling ->
                exceptionHandling.accessDeniedHandler(jwtAccessDeniedHandler)
                exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint)
            }
            .addFilterBefore(JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)
            .headers().frameOptions().disable()

        val authenticationManager = http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .also {
                it.authenticationProvider(jwtAuthenticationProvider)
            }.build()

        http.authenticationManager(authenticationManager)

        return http.build()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

}

@Component
class JwtAuthenticationEntryPoint : SendResponse(), AuthenticationEntryPoint {

    override fun commence(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
        val status = HttpStatus.UNAUTHORIZED

        logger.error(exception) {
            val uri = request.requestURI.orEmpty()
            "[UNAUTHORIZED] uri=$uri, status=$status, exception=$exception"
        }

        sendError(request, response, status.value())
    }
}

@Component
class JwtAccessDeniedHandler : SendResponse(), AccessDeniedHandler {

    override fun handle(request: HttpServletRequest, response: HttpServletResponse, exception: AccessDeniedException) {
        val status = HttpStatus.FORBIDDEN

        logger.error(exception) {
            val uri = request.requestURI.orEmpty()
            "[FORBIDDEN] uri=$uri, status=$status, exception=$exception"
        }

        sendError(request, response, status.value())
    }
}

open class SendResponse {

    fun sendError(request: HttpServletRequest, response: HttpServletResponse, status: Int) {

        val authResultCode = request.getAttribute(AUTH_RESULT_CODE)

        val errorResponse = if (authResultCode != null) {
            val resultCode = authResultCode as ResultCode
            ErrorResponse(resultCode.code, resultCode.message)
        } else {
            ErrorResponse(message = "인증 오류 입니다.")
        }

        response.status = status
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(errorResponse.writeValueAsString())
    }
}


