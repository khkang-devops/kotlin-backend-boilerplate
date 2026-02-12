package com.test.app.common.email.service

import com.test.app.common.email.dto.EmailEventDto
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val javaMailSender: JavaMailSender
) {
    private val logger = KotlinLogging.logger {}

    fun sendMessageAsync(emailEventDto: EmailEventDto) {
        try {
            val message: MimeMessage = javaMailSender.createMimeMessage()
            MimeMessageHelper(message, false, "utf-8").apply {
                setTo(emailEventDto.to.toTypedArray())
                setFrom(emailEventDto.from, emailEventDto.fromName)
                setSubject(emailEventDto.subject)
                setText(emailEventDto.text, true)
                setReplyTo(InternetAddress.parse(emailEventDto.from, false).first())
            }
            javaMailSender.send(message)
        } catch (e: Exception) {
            logger.error { "Email Send Error : ${e.message}" }
        }
    }
}
