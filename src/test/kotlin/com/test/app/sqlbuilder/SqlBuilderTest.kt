package com.test.app.sqlbuilder

import com.test.app.config.gcp.eq
import com.test.app.config.gcp.limits
import io.zeko.db.sql.Query
import io.zeko.db.sql.dsl.inList
import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Test

/**
 * Mybatis Sql Builder Class 와 Zeko Sql Builder 비교.
 * Zeko가 좀더 kotlin 스럽고, string escape 처리등.. 편해 보인다.
 */
internal class SqlBuilderTest {
    private val logger = KotlinLogging.logger {}

    data class Params(val id: String)

    @Test
    fun `zeko sql query builder test`() {
        val toSql = Query()
            .fields("id", "name", "age")
            .from("user join b_user using (id)")
            .groupBy("id", "age")
            .where("id" inList arrayOf(1, 12, 18, 25, 55))
            .where("age" eq "10")
            .limit(1)
            .toSql()

        logger.info { toSql }

        val arrays = arrayOf("1", "2")
        val toSql1 = Query()
            .fields("id", "name", "age")
            .from("user")
            .where("id" inList arrays)
            .limits(1)
            .toSql()

        logger.info { toSql1 }
    }
}
