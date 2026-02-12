package com.test.app.common.teams.service

import com.test.app.common.teams.dto.TeamsRequest
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class TeamsService(
    private val restTemplate: RestTemplate = RestTemplate()
) {
    private val logger = KotlinLogging.logger {}

    /**
     * Incoming Webhook URL로 Teams 메시지 전송
     *
     * @param request TeamsRequest
     * @param webhookUrl Webhook URL
     */
    fun send(request: TeamsRequest, webhookUrl: String) {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }

        val entity = HttpEntity(request.getPayload(), headers)

        try {
            val response = restTemplate.postForEntity(webhookUrl, entity, String::class.java)
            logger.info { "Send Webhook: ${response.statusCode}, ${response.body}" }

            if (!response.statusCode.is2xxSuccessful) {
                logger.error { "Send Webhook Failed!: ${response.statusCode}, ${response.body}" }
            }
        } catch (e: Exception) {
            logger.error { "Send Webhook Failed!: ${e.message}" }
        }

    }
}