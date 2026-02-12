package com.test.app.common.teams.event

import com.test.app.common.teams.dto.TeamsEventDto
import com.test.app.common.teams.service.TeamsService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Async
@Component
class TeamsEventListener(
    private val teamsService: TeamsService
) {

    /**
     * Teams 발송이 필요한 곳에서
     * applicationEventPublisher.publishEvent(TeamsEventDto) 를 전달하여 수행하면됨
     */
    @EventListener
    fun sendTeamsEvent(teamsEventDto: TeamsEventDto) {
        teamsService.send(teamsEventDto.teamsRequest, teamsEventDto.webhookUrl)
    }

}