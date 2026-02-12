package com.test.app

import com.google.cloud.spring.autoconfigure.bigquery.GcpBigQueryAutoConfiguration
import com.google.cloud.spring.autoconfigure.core.GcpContextAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@ConfigurationPropertiesScan
@SpringBootApplication(exclude = [
    LdapRepositoriesAutoConfiguration::class,
    DataSourceAutoConfiguration::class,
    DataSourceTransactionManagerAutoConfiguration::class,
    GcpContextAutoConfiguration::class,
    GcpBigQueryAutoConfiguration::class,
    IntegrationAutoConfiguration::class,
])
class ConsoleApplication

fun main(args: Array<String>) {
    runApplication<com.test.app.ConsoleApplication>(*args)
}
