package com.test.app.common.code

enum class BizTypeCode(val code: String, val codeName: String) {
    EMART("1100", "이마트"),
    EMART_PP("1110", "이마트PP"),
    TRADERS("1200", "트레이더스"),
    NOBRAND("1300", "노브랜드"),
    EVERYDAY("1400", "에브리데이"),
    PKMARKET("1500", "PK마켓"),
    EVERYDAY_B2B("1600", "에브리데이B2B"),
    NONE("NA", "");

    companion object {
        private val map: Map<String, BizTypeCode> = entries.associateBy { it.code }

        fun of(code: String): BizTypeCode {
            return map.getOrDefault(code, NONE)
        }
    }
}
