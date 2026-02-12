package com.test.app.common.code

enum class UserRole(val code: String) {
    USER("USER"), // 일반 user
    PREMIUM("PREMIUM"),
    NONE("NONE");

    fun isNone() = this == NONE

    companion object {
        private val map: Map<String, UserRole> = entries.associateBy { it.code }

        fun of(code: String): UserRole {
            return map.getOrDefault(code, NONE)
        }
    }
}
