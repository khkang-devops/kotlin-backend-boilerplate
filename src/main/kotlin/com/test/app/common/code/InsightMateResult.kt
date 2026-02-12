package com.test.app.common.code

enum class InsightMateResult(val code: String) {
    SUCCESS("0000"),
    PROCESSING("1111"),
    FAIL("9999");

    companion object {
        fun fromCode(code: String): InsightMateResult {
            return entries.first { it.code == code }
        }
    }
}
