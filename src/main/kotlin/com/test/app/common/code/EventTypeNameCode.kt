package com.test.app.common.code

enum class EventTypeNameCode(val code: String, val value: String) {
    EVENT_01("상품에누리", "상품 에누리"),
    EVENT_02("쿠폰에누리", "쿠폰 에누리"),
    EVENT_05("카드사에누리", "카드사 에누리"),
    EVENT_06("S-POINT에누리", "S-Point 에누리"),
    EVENT_ETC("기타 행사", "기타 행사"),
    NONE("", "");

    companion object {
        private val map: Map<String, EventTypeNameCode> = entries.associateBy { it.code }

        fun of(code: String): EventTypeNameCode {
            return map.getOrDefault(code, NONE)
        }
    }
}
