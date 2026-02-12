package com.test.app.common.support

import com.google.cloud.bigquery.FieldValueList
import java.security.MessageDigest

fun String?.sha256(): String {
    val text = this ?: return ""
    return MessageDigest
        .getInstance("SHA-256")
        .digest(text.toByteArray())
        .joinToString("") { "%02x".format(it) }
}


// for bigquery procedure call parameters
fun String?.toParam(): String =
    if (this.isNullOrEmpty()) "''" else "'$this'"
fun List<String>?.toParam(): String {
    return if (this.isNullOrEmpty()) "[]"
    else this.joinToString(",", "[", "]") { it.toParam() }
}
fun Set<String>?.toParam(): String {
    return if (this.isNullOrEmpty()) "[]"
    else this.joinToString(",", "[", "]") { it.toParam() }
}

// for bigquery result
fun FieldValueList.stringValue(name: String, default: String = ""): String =
    if (this.get(name).isNull) default else this.get(name).stringValue

fun FieldValueList.longValue(name: String, default: Long = 0): Long =
    if (this.get(name).isNull) default else this.get(name).longValue

fun FieldValueList.doubleValue(name: String, default: Double = 0.0): Double =
    if (this.get(name).isNull) default else this.get(name).doubleValue