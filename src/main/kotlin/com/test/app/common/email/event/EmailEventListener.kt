package com.test.app.common.email.event

import com.test.app.common.email.dto.EmailEventDto
import com.test.app.common.email.service.EmailService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Async
@Component
class EmailEventListener(
    private val emailService: EmailService
) {

    /**
     * Email 발송이 필요한 곳에서
     * applicationEventPublisher.publishEvent(EmailEventDto) 를 전달하여 수행하면됨
     */
    @EventListener
    fun sendEmailEvent(emailEventDto: EmailEventDto) {
        emailService.sendMessageAsync(emailEventDto)
    }

}