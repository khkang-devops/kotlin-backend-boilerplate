package com.test.app.config.logging

import com.test.app.common.support.writeValueAsString
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.core.env.AbstractEnvironment
import org.springframework.core.env.EnumerablePropertySource

/**
 * 초기 기동시 모든 환경 변수를 로깅하도록 한다.
 */
@Configuration
class PropertyLoggingConfig {

    private val logger = KotlinLogging.logger {}

    @EventListener
    fun handleContextRefresh(event: ContextRefreshedEvent) {
        val env = event.applicationContext.environment

        logger.info { "active profiles = ${env.activeProfiles.toList()}" }

        val sources = (env as AbstractEnvironment).propertySources

        val properties = sources.asSequence()
            .filter { ps -> ps is EnumerablePropertySource }
            .map { ps -> (ps as EnumerablePropertySource).propertyNames.toList() }
            .flatten()
            .filter { propName ->
                !propName.contains("catalina") && !propName.contains("java.class.path")
            }
            .distinct()
            .sorted()
            .associateWith { propName -> env.getProperty(propName)  }

        EnvironmentProperties(properties)

        logger.info { "environment properties" }
        logger.info { EnvironmentProperties(properties).writeValueAsString(false) }
    }

    data class EnvironmentProperties(val environmentProperties: Map<String, String?>)
}
