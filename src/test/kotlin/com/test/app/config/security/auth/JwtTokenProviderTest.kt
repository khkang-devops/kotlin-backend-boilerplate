package com.test.app.config.security.auth

import com.test.app.support.IntegrationTest
import io.github.oshai.kotlinlogging.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@IntegrationTest
internal class JwtTokenProviderTest(
    private val jwtTokenProvider: JwtTokenProvider
) {

    private val logger = KotlinLogging.logger {}

    @Test
    fun `create token test`() {
        val jwtToken = jwtTokenProvider.createTokens("215944", "양해엽")
        logger.info { "jwtToken = $jwtToken" }

        val subject = jwtTokenProvider.getSubject(jwtToken.accessToken)
        logger.info { "subject = $subject" }

        assertThat(subject).isEqualTo("215944")
    }
}
