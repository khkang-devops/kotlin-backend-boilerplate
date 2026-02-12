package com.test.app.api.common.service

import com.test.app.api.common.dto.SampleRequestDto
import com.test.app.api.common.dto.SampleResponseDto
import com.test.app.api.common.dto.ServiceLogRequestDto
import com.test.app.api.common.dto.ServiceLogResponseDto
import com.test.app.bq.sample.SampleClient
import com.test.app.exposed.logs.repository.ServiceLogRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SampleService(
    private val sampleClient: SampleClient,
    private val serviceLogRepository: ServiceLogRepository,
) {
    fun getSampleBigqueryList(req: SampleRequestDto): List<SampleResponseDto> {
        return sampleClient.getSampleList(req)
    }

    @Transactional(readOnly = true)
    fun getSampleExposedList(req: ServiceLogRequestDto): List<ServiceLogResponseDto> {
        return serviceLogRepository.getServiceLogList(req)
            .map { ServiceLogResponseDto(it) }
    }
}