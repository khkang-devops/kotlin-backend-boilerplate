package com.test.app.common.comm

import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Test

internal class BasicsTest {

    private val logger = KotlinLogging.logger {}

    data class Person(var name: String? = null)

    @Test
    fun `null test`() {
        val person:Person = Person()

        val isNull: Boolean? = person.name?.isBlank()

        val plusOperation: (String, String) -> String = { acc: String, s: String -> acc + s }

        buildString {
            append("aaa")
        }

        val sum = { x: Int, y: Int -> x + y }
        val action = { println(42) }

    }

    private fun buildString(builderAction: StringBuilder.() -> Unit): String {
        val sb = StringBuilder()
        sb.builderAction()
        return sb.toString()
    }

    @Test
    fun `sorted test`() {
        val list = listOf("이마트PP", "이마트", "트레이더스")
        logger.info { list.sorted() }
    }

    @Test
    fun `sortedWith test`() {
        val list = listOf("2024년 10월", "2024년 1월", "2024년 2월")
        val result = "2024년 10월".filter { it in '0' .. '9' }.toInt()
        logger.info { result }
        val comparator = compareBy<String> { item -> item.filter { it in '0'..'9' }.toInt() }
        logger.info { list.sortedWith(comparator) }
    }
}