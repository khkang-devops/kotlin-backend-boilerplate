package com.test.app.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "secrets")
data class SecretsProperties(
    val bqDtmpDataCredentials: String,
)
