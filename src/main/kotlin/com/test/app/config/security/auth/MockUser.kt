package com.test.app.config.security.auth

import com.test.app.common.code.UserRole

/**
 *
 * 하드코딩된 예외 처리되는 유저 목록..
 * 먼가 나시으한 방법 고민 필요... (실제 필요할까도 의문...)
 */
object MockUser {

    data class MockRoles(
        val password: String,
        val roles: List<UserRole>,
        val partnerName: String = "이마트",
    )

    private val mockUsers = mapOf(
        "admin" to MockRoles("admin1234!@", listOf(UserRole.PREMIUM)),
        "user" to MockRoles("user1234!@", listOf(UserRole.USER))
    )

    fun isUser(username: String): Boolean = mockUsers.containsKey(username)
    fun isValid(username: String, password: String): Boolean = mockUsers[username]?.password == password
    fun getRoles(username: String): List<UserRole> = mockUsers[username]?.roles ?: listOf()
    fun getPartnerName(username: String): String = mockUsers[username]?.partnerName ?: ""
}


