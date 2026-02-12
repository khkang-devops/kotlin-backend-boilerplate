package com.test.app.common.exception

open class BaseNotFoundEntityException(
    val code: String,
    override val message: String
) : RuntimeException()