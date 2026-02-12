package com.test.app.common.extension

import jakarta.servlet.http.HttpServletRequest
import java.security.MessageDigest

fun HttpServletRequest.getHeaderOrDefault(name: String, default: String) =
    getHeader(name) ?: default

fun String.encode(): String =
    MessageDigest
        .getInstance("SHA-256")
        .digest(this.toByteArray())
        .joinToString("") { "%02x".format(it) }