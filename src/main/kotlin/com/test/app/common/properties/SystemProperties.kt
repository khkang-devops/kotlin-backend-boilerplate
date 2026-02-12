package com.test.app.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "system")
data class SystemProperties(
    val audit: Audit = Audit(Audit.LogProperties(enable = true)),
    val security: Security = Security(Security.AuthProperties(enable = true)),
    val email: Email = Email(),
    val bucketName: String = "s3",
    val backend: Backend = Backend(),
) {
    data class Audit(val log: LogProperties) {
        data class LogProperties(val enable: Boolean)
    }

    data class Security(val auth: AuthProperties) {
        data class AuthProperties(val enable: Boolean)
    }

    data class Email(
        val admin: List<String> = listOf(),
        val passwordResetUrl: String = "",
        val qnaAdmin: List<String> = listOf()
    )

    data class Backend(
        val url: String = "",
    )
}
