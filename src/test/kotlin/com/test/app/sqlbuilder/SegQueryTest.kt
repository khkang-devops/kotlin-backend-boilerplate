package com.test.app.sqlbuilder

import com.test.app.config.gcp.inList
import com.test.app.config.gcp.or
import io.zeko.db.sql.Query
import io.zeko.db.sql.operators.isNull
import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Test

internal class SegQueryTest {
    private val logger = KotlinLogging.logger {}

    @Test
    fun `indent test`() {

        val block = """
            
            abcd
            efg
            
        """.prependIndent("").trim()
        logger.info { block }

        val block2 = """
            
            $block
            higk...
            
        """.trimIndent()

        logger.info { block2 }

        val selectBlock = """
            
            org_cat_code,
            round(sum(pay_amount) / count(distinct concat(cdp_cust_no, p_date)), 3) as pay_amount_per_cust
            round(sum(pay_amount) / count(distinct concat(cdp_cust_no, p_date)), 3) as pay_amount_per_cust
            
        """.prependIndent("    ").trim()

        logger.info { selectBlock }

        val result = """
            
            select
                'seg' as code,
                $selectBlock
            from ds_cdp_tag_emart.tb_off_purchase_product_org_cat
            
        """.trimIndent()

        logger.info { result }
    }

    @Test
    fun `inList null test`() {
        val bainGrades:List<Int?> = listOf(1, null)
        logger.info { bainGrades }

        val query = Query().fields("cdp_cust_no")
            .from("ds_cdp_tag_emart.tbm_off_member")

        if (bainGrades.contains(null)) {
            query.where("bain_grade" inList bainGrades.filterNotNull() or isNull("bain_grade"))
        } else {
            query.where("bain_grade" inList bainGrades.filterNotNull())
        }

        logger.info { query.toSql() }
    }
}
