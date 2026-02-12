package com.test.app.common.support

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule

val objectMapper = ObjectMapper().apply {
    registerModule(KotlinModule.Builder().build())
    registerModule(JavaTimeModule())

    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
}

fun Any.writeValueAsString(pretty: Boolean = false): String =
    if (pretty)
        objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this)
    else
        objectMapper.writeValueAsString(this)
