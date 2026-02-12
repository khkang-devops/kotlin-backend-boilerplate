package com.test.app.common.exception

open class UnexpectedDataException(
    val code: String,
    override val message: String
): RuntimeException()