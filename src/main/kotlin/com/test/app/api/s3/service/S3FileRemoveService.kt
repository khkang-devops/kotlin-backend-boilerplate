package com.test.app.api.s3.service

import org.springframework.stereotype.Service

@Service
class S3FileRemoveService(
    private val s3Service: S3Service,
) {
    fun removeAttachment(
        storedAttachment: List<String?>?,
        inputAttachment: List<String?>?
    ) {
        if (storedAttachment.isNullOrEmpty()) {
            return
        }

        if (inputAttachment.isNullOrEmpty()) {
            storedAttachment.forEach { s3Service.deleteObject("e2b/$it") }
        } else {
            storedAttachment
                .filter { !inputAttachment.contains(it) }
                .forEach { s3Service.deleteObject("e2b/$it") }
        }
    }
}