package com.test.app.config.web

import com.test.app.config.security.auth.UserPrincipalResolver
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.resource.PathResourceResolver

@Configuration
class WebMvcConfig(
    private val userPrincipalResolver: UserPrincipalResolver
) : WebMvcConfigurer {

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry
            .addResourceHandler("/views/**/*")
            .addResourceLocations("classpath:/static/")
            .resourceChain(true)
            .addResolver(object: PathResourceResolver() {
                override fun getResource(resourcePath: String, location: Resource): Resource {
                    val reqResource = location.createRelative(resourcePath)
                    return if (reqResource.exists() && reqResource.isReadable) reqResource else ClassPathResource("/static/index.html")
                }
            })
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(userPrincipalResolver)
    }
}
