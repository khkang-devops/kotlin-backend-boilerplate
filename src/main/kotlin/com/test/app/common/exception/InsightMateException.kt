package com.test.app.common.exception

import com.test.app.common.code.ResultCode

open class InsightMateException(resultCode: ResultCode, suffixMsg : String = "") : BizException(resultCode, suffixMsg)

