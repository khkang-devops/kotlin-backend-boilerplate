package com.test.app.api.common

import com.test.app.common.code.ResultCode
import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema


@JsonInclude(JsonInclude.Include.NON_NULL)
class ApiResponse<T>(
    @Schema(description = "응답 코드", example = "0000")
    val code: String = ResultCode.SUCCESS.code,

    @Schema(description = "응답 메시지", example = "성공")
    val message: String? = null,

    @Schema(description = "응답 DTO")
    val data: T? = null,
) {

    constructor(resultCode: ResultCode): this(
        code = resultCode.code,
        message = resultCode.message,
    )

    constructor(resultCode: ResultCode, suffixMsg: String): this(
        code = resultCode.code,
        message = "${resultCode.message} ($suffixMsg)",
    )
}
