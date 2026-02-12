package com.test.app.config.gcp

import com.test.app.common.properties.BigQueryProperties
import com.test.app.common.properties.SecretsProperties
import com.google.api.gax.core.CredentialsProvider
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.bigquery.BigQuery
import com.google.cloud.bigquery.BigQueryOptions
import com.google.cloud.spring.core.UserAgentHeaderProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.ByteArrayInputStream

@Configuration
class DtmpBQ(
    private val secretsProperties: SecretsProperties,
    private val bigQueryProperties: BigQueryProperties
) {

    companion object {
        private const val PREFIX = "DtmpBQ"
        const val CLIENT = PREFIX + "Client"
    }

    private val credentialsProvider: CredentialsProvider by lazy {
        val googleCredentials: GoogleCredentials = GoogleCredentials.fromStream(
            ByteArrayInputStream(secretsProperties.bqDtmpDataCredentials.toByteArray())
        )

        FixedCredentialsProvider.create(googleCredentials)
    }

    @Bean(name = [CLIENT])
    fun bigQuery(): BigQuery {
        return BigQueryOptions.newBuilder()
            .setProjectId(bigQueryProperties.projectId)
            .setCredentials(credentialsProvider.credentials)
            .setHeaderProvider(UserAgentHeaderProvider(DtmpBQ::class.java))
            .setLocation("asia-northeast3")
            .build()
            .service
    }
}
