package com.test.app.api.common.web

import com.test.app.api.common.ApiResponse
import com.test.app.api.common.dto.SampleRequestDto
import com.test.app.api.common.dto.SampleResponseDto
import com.test.app.api.common.dto.ServiceLogRequestDto
import com.test.app.api.common.dto.ServiceLogResponseDto
import com.test.app.api.common.service.SampleService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SampleController(
    private val sampleService: SampleService
) {
    @Operation(summary = "bigquery 프로시저 호출 샘플")
    @PostMapping("/api/sample/bigquery")
    fun sampleBigqueryList(@RequestBody req: SampleRequestDto): ApiResponse<List<SampleResponseDto>> {
        return ApiResponse(data = sampleService.getSampleBigqueryList(req))
    }

    @Operation(summary = "exposed 호출 샘플")
    @PostMapping("/api/sample/exposed")
    fun sampleExposedList(@RequestBody req: ServiceLogRequestDto): ApiResponse<List<ServiceLogResponseDto>> {
        return ApiResponse(data = sampleService.getSampleExposedList(req))
    }
}