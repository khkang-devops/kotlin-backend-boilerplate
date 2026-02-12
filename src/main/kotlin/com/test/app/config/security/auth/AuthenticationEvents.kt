package com.test.app.config.security.auth

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import org.springframework.security.authentication.AuthenticationEventPublisher
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.security.authentication.event.LoggerListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Configuration
class AuthenticationEventConfig {

    @Bean
    fun authenticationEventPublisher(applicationEventPublisher: ApplicationEventPublisher): AuthenticationEventPublisher {
        return DefaultAuthenticationEventPublisher(applicationEventPublisher)
    }

    @Bean
    fun loggerListener(): LoggerListener = LoggerListener()
}

@Component
class AuthenticationEvents {
    @EventListener
    fun onSuccess(success: AuthenticationSuccessEvent) {
        logger.info { success }
    }

    @EventListener
    fun onFailure(failure: AbstractAuthenticationFailureEvent) {
        logger.info { failure }
    }
}
