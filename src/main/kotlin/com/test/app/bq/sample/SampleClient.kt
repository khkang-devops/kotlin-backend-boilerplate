package com.test.app.bq.sample

import com.test.app.api.common.dto.SampleRequestDto
import com.test.app.api.common.dto.SampleResponseDto
import com.test.app.common.properties.BigQueryProperties
import com.test.app.common.support.stringValue
import com.test.app.config.gcp.DtmpBQ
import com.test.app.config.gcp.doQuery
import com.google.cloud.bigquery.BigQuery
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class SampleClient(
    @param:Qualifier(DtmpBQ.CLIENT) private val bigQuery: BigQuery,
    bigQueryProperties: BigQueryProperties
) : SampleQuery(bigQueryProperties.datasetName) {
    private val logger = KotlinLogging.logger {}

    fun getSampleList(req: SampleRequestDto): List<SampleResponseDto> {
        val query = sampleList(req)
        logger.info { "getSampleList query = $query" }

        val (resultList, _) = bigQuery.doQuery(query) { tableResult ->
            tableResult.iterateAll().map { fvl ->
                logger.info { "getSampleList fvl = $fvl" }
                SampleResponseDto(
                    biztpCd = fvl.stringValue("biztp_cd"),
                    mcodeNm = fvl.stringValue("mcode_nm"),
                    dcodeNm = fvl.stringValue("dcode_nm"),
                    prdtCd = fvl.stringValue("prdt_cd"),
                    prdtNm = fvl.stringValue("prdt_nm"),
                )
            }
        }

        return resultList
    }
}