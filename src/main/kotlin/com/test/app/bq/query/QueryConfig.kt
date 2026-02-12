package com.test.app.bq.query

open class QueryConfig(val datasetName: String) {

    fun routineName(name: String): String {
        return "`emartgcp-p-cdp.${datasetName}.${name}`"
    }
}
