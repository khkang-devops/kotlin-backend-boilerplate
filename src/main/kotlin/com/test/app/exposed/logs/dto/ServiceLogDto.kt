package com.test.app.exposed.logs.dto

class ServiceLogDto {
    data class Command(
        val userId: String,
        val eventId: String,
        val eventDesc: String,
        val event: String,
    )
}