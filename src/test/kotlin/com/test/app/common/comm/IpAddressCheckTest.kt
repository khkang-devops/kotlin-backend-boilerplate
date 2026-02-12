package com.test.app.common.comm

import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.security.web.util.matcher.IpAddressMatcher

internal class IpAddressCheckTest {
    private val logger = KotlinLogging.logger {}

    private fun matches(ip: String, subnet: String): Boolean {
        val ipAddressMatcher = IpAddressMatcher(subnet)
        return ipAddressMatcher.matches(ip)
    }

    @Test
    fun `ip address check`() {

        logger.info { matches("192.168.2.1", "0.0.0.0/0") } // true
        logger.info { matches("10.10.2.1", "0.0.0.0/0") } // true
        logger.info { matches("192.168.2.1", "192.168.2.1") } // true
        logger.info { matches("192.168.2.1", "192.168.2.0/32") } // false
        logger.info { matches("192.168.2.5", "192.168.2.0/24") } // true
        logger.info { matches("92.168.2.1", "fe80:0:0:0:0:0:c0a8:1/120") } // false
        logger.info { matches("fe80:0:0:0:0:0:c0a8:11", "fe80:0:0:0:0:0:c0a8:1/120") } // true
        logger.info { matches("fe80:0:0:0:0:0:c0a8:11", "fe80:0:0:0:0:0:c0a8:1/128") } // false
        logger.info { matches("fe80:0:0:0:0:0:c0a8:11", "192.168.2.0/32") } // false

    }
}