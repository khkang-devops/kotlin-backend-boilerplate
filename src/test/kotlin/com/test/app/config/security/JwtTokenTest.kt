package com.test.app.config.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Test
import java.util.*

internal class JwtTokenTest {
    private val logger = KotlinLogging.logger {}

    @Test
    fun `secretKey create test`() {
        val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

        val secretString = Encoders.BASE64.encode(secretKey.encoded)
        logger.info { secretString }
    }

    @Test
    fun `create token test`() {

        val decode = Decoders.BASE64.decode("4jl0UIDNtNkIjxOgZwnQ9pgkrTCXhYgfNubQh0hEwXY=")
        val key = Keys.hmacShaKeyFor(decode)

        val claims = Jwts.claims().setSubject("215944")
        val now = Date()
        val expiration = Date(now.time + 10000)
        val token = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        logger.info { "token = $token" }

        val claimsJws = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)

        logger.info { "claimsJws = $claimsJws" }
        logger.info { "claimsJws.body = ${claimsJws.body}" }
        logger.info { "claimsJws.body.subject = ${claimsJws.body.subject}" }


    }
}
