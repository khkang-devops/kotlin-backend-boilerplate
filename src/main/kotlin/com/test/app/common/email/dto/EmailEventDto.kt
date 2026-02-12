package com.test.app.common.email.dto

data class EmailEventDto(
    val to: List<String>,
    val from: String = "noreply@emart.com",
    val fromName : String = "[이마트-PDI]",
    val subject: String,
    val text: String,
)
