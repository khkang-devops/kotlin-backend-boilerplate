package com.test.app.common.util

import org.springframework.stereotype.Component
import java.security.MessageDigest
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@Component
class TaskIdGenerator {

    fun generate(username: String): String {
        val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
        val salt = Random.nextInt(1000, 9999)
        val raw = "$username-$date-$salt"

        val hashBytes = MessageDigest.getInstance("SHA-256")
            .digest(raw.toByteArray())

        val base62 = hashBytes
            .take(8)
            .joinToString("") { "%02x".format(it) }

        return "task-$base62"
    }

}