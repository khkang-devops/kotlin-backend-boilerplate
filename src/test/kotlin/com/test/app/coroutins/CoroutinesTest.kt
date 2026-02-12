package com.test.app.coroutins

import kotlinx.coroutines.*
import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Test

internal class CoroutinesTest {
    private val logger = KotlinLogging.logger {}

    suspend fun doSomething(idx: Int): String {
        delay(1)
        return "$idx : abcde"
    }

    suspend fun doWorld() {
        delay(1000L)
        println("World!")
    }

    @Test
    fun `coroutines test`() {
        logger.info { "start" }

        runBlocking(Dispatchers.IO) {

            listOf(1, 2, 3, 4, 5).map {
               async {
                   delay(1000L)
                   logger.info { "run.." }

                   doSomething(it)
               }
           }.awaitAll().forEach { logger.info { it } }

        }
        logger.info { "end" }

    }
}
