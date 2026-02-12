package com.test.app.config.gcp

import com.google.cloud.RetryOption
import com.google.cloud.bigquery.*
import io.github.oshai.kotlinlogging.KotlinLogging
import org.joda.time.DateTime
import org.threeten.bp.Duration
import java.util.*


private val logger = KotlinLogging.logger {}

private const val JOB_TIME_OUT_MS = 5 * 60 * 1000L // 5분

/**
 * bigQuery.query("SELECT column FROM table") { result ->
 *   result.iterateAll.forEach { it.toString() }
 *   result
 * }
 */
fun <T> BigQuery.doQuery(
    query: String,
    fn: (tableResult: TableResult) -> T
): Pair<T, TableId> {

    val jobConfiguration = QueryJobConfiguration.newBuilder(query)
        .setJobTimeoutMs(JOB_TIME_OUT_MS)
        .build()

    return this.doQuery(jobConfiguration, fn)
}

/**
 * val query = "SELECT column FROM table"
 * val jobConfiguration = QueryJobConfiguration.newBuilder(query).build()
 * bigQuery.query(jobConfiguration) { result ->
 *   result.iterateAll.forEach { it.toString() }
 * }
 */
fun <T> BigQuery.doQuery(
    queryJobConfiguration: QueryJobConfiguration,
    fn: (tableResult: TableResult) -> T,
): Pair<T, TableId> {

    val (tableResult, destinationTableId, _) = this.doInternal(queryJobConfiguration)

    return fn(tableResult!!) to destinationTableId
}

fun BigQuery.doPageQuery(
    query: String,
    pageSize: Long = 10_000L,
    fn: (values: Iterable<FieldValueList>) -> Unit,
) {
    val jobConfiguration = QueryJobConfiguration.newBuilder(query)
        .setJobTimeoutMs(JOB_TIME_OUT_MS)
        .build()

    this.doPageQuery(jobConfiguration, pageSize, fn)
}

/**
 * ref. https://github.com/googleapis/java-bigquery/blob/main/samples/snippets/src/main/java/com/example/bigquery/QueryPagination.java
 */
fun BigQuery.doPageQuery(
    queryJobConfiguration: QueryJobConfiguration,
    pageSize: Long = 10_000L,
    fn: (values: Iterable<FieldValueList>) -> Unit,
) {

    var (tableResult, _, _) = this.doInternal(queryJobConfiguration, true, pageSize)

    if (tableResult == null) {
        fn(listOf<FieldValueList>().asIterable())
        return
    }

    // First Page
    fn(tableResult.values)

    while (tableResult!!.hasNextPage()) {
        tableResult = tableResult.nextPage
        // Remaining Pages
        fn(tableResult.values)
    }
}

/**
 * table result 가 필요 없는 update, insert, delete query 에서 사용 하도록 한다.
 */
fun BigQuery.doExecute(query: String): DmlStats {
    return this.doExecute(QueryJobConfiguration.of(query))
}

fun BigQuery.doExecute(queryJobConfiguration: QueryJobConfiguration): DmlStats {
    val (_, _, stats) = this.doInternal(queryJobConfiguration, false)

    return stats.dmlStats?.let {

        val insertedRowCount: Long = it.insertedRowCount ?: 0L
        val updatedRowCount: Long = it.updatedRowCount ?: 0L
        val deletedRowCount: Long = it.deletedRowCount ?: 0L

        DmlStats(insertedRowCount, updatedRowCount, deletedRowCount)

    } ?: DmlStats()
}

fun TableId.toStr(): String {
    return "${this.project}.${this.dataset}.${this.table}"
}

/**
 * BQ SQL을 query와 transform 결과를 전달하여 실행
 *
 * @param query 실행할 때 쿼리
 * @param transform T type 으로 변환하는 함수
 * @return 쿼리 실행 결과
 */
fun <T> BigQuery.executeQuery(query: String, transform: (FieldValueList) -> T): List<T> =
    this.doQuery(query) { it.iterateAll().map(transform) }.first

private fun BigQuery.doInternal(
    queryJobConfiguration: QueryJobConfiguration,
    doGetQueryResult: Boolean = true,
    pageSize: Long? = null,
): Triple<TableResult?, TableId, JobStatistics.QueryStatistics> {

    val job = this.create(JobInfo.of(createJobId(), queryJobConfiguration))

    val queryJob = job.waitFor(
        RetryOption.totalTimeout(Duration.ofMinutes(30))
    )

    if (queryJob == null) {
        throw RuntimeException("Job no longer exists")
    } else if (queryJob.status.error != null) {
        throw RuntimeException(queryJob.status.error.toString())
    }

//    val parentJobId = queryJob.jobId.job
//    val childJobs: Page<Job> = this.listJobs(BigQuery.JobListOption.parentJobId(parentJobId))
//    childJobs.values.forEach { logger.info { "jobId=${it.jobId} ${it.}" } }

    val tableResult = if (doGetQueryResult) {
        if (pageSize != null) queryJob.getQueryResults(BigQuery.QueryResultsOption.pageSize(pageSize))
        else queryJob.getQueryResults()
    } else null

    val destinationTableId = queryJob.getConfiguration<QueryJobConfiguration>().destinationTable
    val queryStatistics = queryJob.getStatistics<JobStatistics.QueryStatistics>()

    queryStatistics(queryStatistics, tableResult, destinationTableId)

    return Triple(tableResult, destinationTableId, queryStatistics)
}

private fun createJobId(): JobId {
    val id = listOf(
        "dtmp",
        DateTime.now().toString("yyyyMMddHHmmss"),
        UUID.randomUUID().toString().replace("-", "")
    ).joinToString("-")

    return JobId.of(id)
}

private fun queryStatistics(
    stats: JobStatistics.QueryStatistics,
    tableResult: TableResult?,
    tableId: TableId
) {

    val dmlStats = stats.dmlStats?.let {
        listOf(
            "dmlStats",
            " - inserted row count ${it.insertedRowCount}",
            " - updated row count ${it.updatedRowCount}",
            " - deleted row count ${it.deletedRowCount}",
        ).joinToString("\n")
    } ?: "dmlStats: None"

    val statistics = listOf(
        "query job statistics",
        "read rows: ${stats.queryPlan?.sumOf { it.recordsRead } ?: "None"}",
        "write rows: ${stats.queryPlan?.sumOf { it.recordsWritten } ?: "None"}",
        "total bytes billed: ${stats.totalBytesBilled}",
        "total bytes processed: ${stats.totalBytesProcessed}",
        "referenced tables: ${stats.referencedTables?.joinToString(",") ?: "None"}",
        "destination table: ${tableId.toStr()}",
        dmlStats,
        "query result row count: ${tableResult?.totalRows}",
    ).joinToString("\n")

    logger.info { statistics }
}

data class DmlStats(
    val insertedRowCount: Long = 0L,
    val updatedRowCount: Long = 0L,
    val deletedRowCount: Long = 0L,
)