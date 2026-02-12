package com.test.app.common.code

enum class ClassifyCode(
    val code: String,
    val codeName: String
) {
    GCODE("gcode", "대분류"),
    MCODE("mcode", "중분류"),
    DCODE("dcode", "소분류");

    companion object {
        fun ofCode(code: String) = entries.firstOrNull { it.code == code }
    }
}