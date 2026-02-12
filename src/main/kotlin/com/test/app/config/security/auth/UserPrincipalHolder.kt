package com.test.app.config.security.auth

import com.test.app.common.code.UserRole
import org.springframework.security.core.context.SecurityContextHolder

object UserPrincipalHolder {

    fun getUserPrincipal(): UserPrincipal {
        val context = SecurityContextHolder.getContext()

        if (context != null && context.authentication != null) {
            return if (context.authentication.principal is UserPrincipal) {
                context.authentication.principal as UserPrincipal
            } else {
                UserPrincipal()
            }
        }

        return UserPrincipal()
    }

    fun getSwitchUsernameOrDefault(): String {
        val userPrincipal = getUserPrincipal()
        return if (userPrincipal.isPresentSwitchUsername()) userPrincipal.getSwitchUsername() else userPrincipal.username
    }

    fun getUsername(): String = getUserPrincipal().username.ifBlank { "NA" }
    fun isPremiumUser(): Boolean = getAuthorities().toSet().contains(UserRole.PREMIUM.code)
    fun getGroups(): List<String> = getUserPrincipal().groups ?: emptyList()
    private fun getAuthorities(): List<String> = getUserPrincipal().authorities.map { it.authority }
}
