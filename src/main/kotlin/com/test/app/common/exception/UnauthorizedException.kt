package com.test.app.common.exception

import com.test.app.common.code.ResultCode

class UnauthorizedException(resultCode: ResultCode, suffixMsg: String = "") : BizException(resultCode, suffixMsg)
