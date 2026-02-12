package com.test.app.api.s3.web

import com.test.app.api.common.ApiResponse
import com.test.app.api.s3.service.S3Service
import com.test.app.common.code.ResultCode
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class S3Controller(
    private val s3Service: S3Service
) {

    /**
     * objectKey : doc/manual.pdf, doc/fag.pdf
     */
    @GetMapping("/api/s3/pre-signed-url")
    fun getPreSignedUrl(
        @RequestParam objectKey: String,
    ): ApiResponse<String> {
        return ApiResponse(data=s3Service.createSignedUrl("e2b/$objectKey"))
    }

    @DeleteMapping("/api/s3")
    fun deleteFile(@RequestParam("fileName") fileName: String): ApiResponse<Void> {
        s3Service.deleteObject("e2b/$fileName")
        return ApiResponse(ResultCode.SUCCESS)
    }

    @GetMapping("/api/s3/download/doc/{fileName}")
    fun downloadDocFile(
        @PathVariable fileName: String,
    ): ResponseEntity<Resource>{
        val resource = s3Service.getResource("e2b/doc/${fileName}")

        val headers: HttpHeaders = HttpHeaders().apply {
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${fileName}")
        }

        return ResponseEntity<Resource>(resource, headers, HttpStatus.OK)
    }
}