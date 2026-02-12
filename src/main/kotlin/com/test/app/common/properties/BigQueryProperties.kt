package com.test.app.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "big-query")
data class BigQueryProperties(
    val projectId: String,
    val datasetName: String,
)