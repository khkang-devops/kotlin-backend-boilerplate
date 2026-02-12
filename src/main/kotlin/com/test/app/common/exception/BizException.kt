package com.test.app.common.exception

import com.test.app.common.code.ResultCode


open class BizException(private val resultCode: ResultCode, private val suffixMsg: String) :
    RuntimeException(
        if (suffixMsg.isBlank())
            resultCode.message
        else
            "${resultCode.message} ($suffixMsg)"
    ) {

        fun code(): String = resultCode.code
        fun message(): String =
            if (suffixMsg.isBlank()) {
                resultCode.message
            } else if (resultCode.code == "5002") {
                if (suffixMsg=="5") {
                    "로그인 5회 실패하였습니다. <br/>관리자에게 비밀번호 초기화 요청해주세요."
                } else {
                    "로그인 ${suffixMsg}회 실패하였습니다. <br/>5회 연속 실패할 경우 로그인 계정이 잠기게 되므로 정확히 입력해주시길 바랍니다."
                }
            } else {
                "${resultCode.message} ($suffixMsg)"
            }
    }