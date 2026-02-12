package com.test.app.config.persistence

import com.zaxxer.hikari.HikariDataSource
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jetbrains.exposed.v1.jdbc.Database
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

@Configuration
class MetaDBConfig {
    private val logger = KotlinLogging.logger {}

    @Bean
    @ConfigurationProperties(prefix = "persistence.meta.datasource")
    fun metaDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Primary
    @Bean
    fun metaDataSource(
        @Qualifier("metaDataSourceProperties") dataSourceProperties: DataSourceProperties
    ): HikariDataSource {
        logger.info {"[MetaDB] url = ${dataSourceProperties.url}, username = ${dataSourceProperties.username}"}

        return dataSourceProperties
            .initializeDataSourceBuilder()
            .type(HikariDataSource::class.java)
            .build()
    }

    @Bean
    fun exposedDataSource(@Qualifier("metaDataSource") dataSource: DataSource): Database {
        return Database.connect(dataSource)
    }
}