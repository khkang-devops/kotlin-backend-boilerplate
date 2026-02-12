package com.test.app.common.code

import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class UserRoleTest {

    private val logger = KotlinLogging.logger {}

    @Test
    fun enumTest() {
        UserRole.of("aaa").also { logger.info { it }  }

        assertThrows(IllegalArgumentException::class.java) {
            UserRole.valueOf("aaa")
        }
    }
}
