package com.test.app.bq.sample

import com.test.app.api.common.dto.SampleRequestDto
import com.test.app.bq.query.QueryConfig
import com.test.app.common.support.toParam

open class SampleQuery(datasetName: String) : QueryConfig(datasetName) {
    fun sampleList(req: SampleRequestDto): String {
        val routine = routineName("dash_promotion_list")

        val param = listOf(
            req.startDate.toParam(),
            req.endDate.toParam(),
            req.biztpCd.toParam(),
            req.mcodeCds.toParam(),
            req.dcodeCds.toParam(),
            req.wonUnit.toParam()
        ).joinToString(",", "(", ")")

        return "call $routine $param"
    }
}