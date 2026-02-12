package com.test.app.config.swagger

import com.test.app.common.properties.SystemProperties
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.media.StringSchema
import io.swagger.v3.oas.models.parameters.Parameter
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.customizers.OperationCustomizer
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.HandlerMethod

@Configuration
class SwaggerConfig(
    private val systemProperties: SystemProperties,
) {
    // refresh_token 헤더추가
    @Bean
    fun globalHeader() = OperationCustomizer { operation: Operation, _: HandlerMethod ->
        operation.addParametersItem(Parameter()
            .`in`(ParameterIn.HEADER.toString())
            .schema(StringSchema().name("Refresh-Token"))
            .name("Refresh-Token"))
        operation
    }

    @Bean
    fun authGroupedOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("01. auth")
            .addOpenApiCustomizer {
                it.security(listOf(SecurityRequirement().addList("access-token")))
            }
            .addOperationCustomizer(operationCustomize())
            .pathsToMatch("/auth/**")
            .build()
    }

    @Bean
    fun actuatorGroupedOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("02. actuator")
            .addOpenApiCustomizer {
                it.security(listOf(SecurityRequirement().addList("access-token")))
            }
            .addOperationCustomizer(operationCustomize())
            .pathsToMatch("/actuator/**")
            .build()
    }

    @Bean
    fun sampleGroupedOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("03. sample")
            .addOpenApiCustomizer {
                it.security(listOf(SecurityRequirement().addList("access-token")))
            }
            .addOperationCustomizer(operationCustomize())
            .pathsToMatch("/api/sample/**")
            .build()
    }

    @Bean
    fun openApi(): OpenAPI {
        val info = Info()
            .version("1.0.0")
            .description("E-Connect API Server on Emart products.")
            .title("E-Connect Backend API Server")

        val authSetting = Components()
            .addSecuritySchemes(
                "access-token",
                SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .`in`(SecurityScheme.In.HEADER)
                    .name("Authentication")
            )

        return OpenAPI()
            .components(authSetting)
            .info(info)
            .addServersItem(Server().url(systemProperties.backend.url))
    }

    fun operationCustomize(): OperationCustomizer {
        return OperationCustomizer { operation: Operation, handlerMethod: HandlerMethod ->
            val methodAnnotation: DisableSwaggerSecurity? =
                handlerMethod.getMethodAnnotation(DisableSwaggerSecurity::class.java)

            // DisableSecurity 어노테이션있을시 스웨거 시큐리티 설정 삭제
            if (methodAnnotation != null) {
                operation.security = listOf()
            }

            operation
        }
    }
}