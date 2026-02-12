package com.test.app.common.code

enum class LifeStageGroupCode(val code: String, val value: String) {
    UNIT_1("1", "사회초년생"),
    UNIT_2("2", "신혼부부"),
    UNIT_3("3", "육아가족"),
    UNIT_4("4", "학생가족"),
    UNIT_5("5", "시니어"),
    NONE("", "");

    companion object {
        private val map: Map<String, LifeStageGroupCode> = entries.associateBy { it.code }

        fun of(code: String): LifeStageGroupCode {
            return map.getOrDefault(code, NONE)
        }
    }
}
