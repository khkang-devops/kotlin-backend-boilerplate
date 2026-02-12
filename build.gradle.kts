import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.12"
    id("io.spring.dependency-management") version "1.1.3"

    val kotlinVersion = "2.2.0"
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
}

group = "com.emart"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

val buildEnv = System.getProperty("build.env") ?: "local"

extra.apply {
    set("spring.cloud.version", "2022.0.3")
    set("spring.cloud.gcp.version", "4.8.0")
    set("aws.spring.cloud.version", "3.0.2")
    set("kotlinx.coroutines.version", "1.6.4")
    set("kotest.version", "5.9.1")
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("spring.cloud.version")}")
        mavenBom("com.google.cloud:spring-cloud-gcp-dependencies:${property("spring.cloud.gcp.version")}")
        mavenBom("io.awspring.cloud:spring-cloud-aws-dependencies:${property("aws.spring.cloud.version")}")
    }
}

dependencies {
    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // kotlin coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${property("kotlinx.coroutines.version")}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:${property("kotlinx.coroutines.version")}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${property("kotlinx.coroutines.version")}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${property("kotlinx.coroutines.version")}")

    // spring boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-ldap")
    implementation("org.springframework.boot:spring-boot-starter-mail")

    // annotation processor
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    // spring security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // spring cloud
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
    implementation("io.github.openfeign:feign-okhttp")

    // aws spring cloud
    implementation("io.awspring.cloud:spring-cloud-aws-starter-secrets-manager")
    implementation("io.awspring.cloud:spring-cloud-aws-starter-s3")

    // aws sts 의존성 추가
    implementation("software.amazon.awssdk:sts")

    // gcp bigquery
    implementation("com.google.cloud:spring-cloud-gcp-starter-bigquery")

    // jasypt
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.4")

    // exposed
    implementation("org.jetbrains.exposed:exposed-core:1.0.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:1.0.0")
    implementation("org.jetbrains.exposed:exposed-spring-boot-starter:1.0.0")
    implementation("org.jetbrains.exposed:exposed-java-time:1.0.0")
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")
    implementation("com.github.vertical-blank:sql-formatter:2.0.5")

    // webClient
    implementation ("org.springframework.boot:spring-boot-starter-webflux")

    // actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // logging
    implementation("io.github.oshai:kotlin-logging-jvm:6.0.3")
    implementation("org.zalando:logbook-spring-boot-starter:3.9.0")
    implementation("org.zalando:logbook-logstash:3.7.2")

    // zeko sql builder
    implementation("io.zeko:zeko-sql-builder:1.4.0") {
        exclude(group = "io.vertx")
        exclude(group = "io.zeko", module = "zeko-data-mapper")
    }

    // csv
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.9.3")

    // openapi swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    testImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.2")

    // jdbc
    implementation("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(group = "org.mockito")
    }
    testImplementation("com.ninja-squad:springmockk:3.1.0")

    testImplementation("io.kotest:kotest-runner-junit5:${property("kotest.version")}")
    testImplementation("io.kotest:kotest-assertions-core:${property("kotest.version")}")
    testImplementation("io.kotest:kotest-property:${property("kotest.version")}")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        freeCompilerArgs.set(listOf("-Xjsr305=strict"))
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootJar {
    archiveFileName.set("app.jar")
}