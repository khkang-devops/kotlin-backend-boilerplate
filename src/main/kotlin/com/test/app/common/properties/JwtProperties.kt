package com.test.app.common.properties

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration
import javax.crypto.SecretKey

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secret: String,
    val accessTokenValidity: Duration,
    val refreshTokenValidity: Duration
) {
    fun getSecretKey(): SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
    fun getSignatureAlgorithm(): SignatureAlgorithm = SignatureAlgorithm.HS256
}