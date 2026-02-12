package com.test.app.common.code

enum class InsightReportTypeCode(val typeName: String) {
    TREND("트렌드 리포트"),
    CATEGORY("카테고리 리포트");

    companion object {

        fun isCategory(code: InsightReportTypeCode) = code == CATEGORY
    }
}