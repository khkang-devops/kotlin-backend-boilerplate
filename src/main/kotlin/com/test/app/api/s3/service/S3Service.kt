package com.test.app.api.s3.service

import com.test.app.common.properties.SystemProperties
import io.awspring.cloud.s3.S3Template
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration


@Service
class S3Service(
    private val systemProperties: SystemProperties,
    private val s3Template: S3Template,
    private val resourceLoader: ResourceLoader
) {

    private val logger = KotlinLogging.logger {}

    fun createSignedUrl(objectKey: String): String {
        val url = s3Template.createSignedPutURL(systemProperties.bucketName, objectKey, 5.minutes.toJavaDuration())
        return url.toString()
    }

    fun getResource(objectKey: String): Resource {
        val location = "s3://${systemProperties.bucketName}/$objectKey"
        return resourceLoader.getResource(location)
    }

    fun deleteObject(objectKey: String) {
        s3Template.deleteObject(systemProperties.bucketName, objectKey)
    }

    fun downloadZipForMultiFiles(objectKey: String, fileNames: List<String?>): Resource {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val zipOutputStream = ZipOutputStream(byteArrayOutputStream)

        fileNames.forEach { fileName ->
            if (fileName.isNullOrEmpty()) {
                return@forEach
            }

            try {
                val s3Resource = getResource("${objectKey}/${fileName}")
                s3Resource.inputStream.use { inputStream ->
                    val zipEntry = ZipEntry(fileName)
                    zipOutputStream.putNextEntry(zipEntry)
                    zipOutputStream.write(inputStream.readAllBytes())
                    zipOutputStream.closeEntry()
                }
            } catch (e: Exception) {
                println("Error $fileName: ${e.message}")
            }
        }
        zipOutputStream.finish()

        return ByteArrayResource(byteArrayOutputStream.toByteArray())
    }
}