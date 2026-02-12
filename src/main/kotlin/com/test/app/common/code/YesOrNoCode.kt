package com.test.app.common.code

enum class YesOrNoCode(
    val codeName: String
) {

    Y("Y"), N("N");

    companion object {
        fun isNo(code: String) =
            N.codeName == code
    }
}