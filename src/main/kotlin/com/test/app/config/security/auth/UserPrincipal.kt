package com.test.app.config.security.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserPrincipal(
    private val username: String = "", // login id
    private val password: String = "",
    private val authorities: Set<GrantedAuthority> = setOf(),
    private val switchUsername: String = "", // switch user id
    val userDesc: String = "", // user account name
    val uri: String = "",
    val sourceIp: String = "",
    val partnerName: String = "", // 협력사 명 (ven_nm)
    val deptNm: String? = "", // 부서 이름
    val biztps: List<String>? = listOf(), // 사용자 구매업태
    val groups: List<String>? = listOf(), // 사용자 그룹명
) : UserDetails {

    override fun getAuthorities(): Set<GrantedAuthority> = authorities
    override fun getUsername(): String = username
    override fun getPassword(): String = password
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

    fun getSwitchUsername(): String = switchUsername
    fun isPresentSwitchUsername(): Boolean = switchUsername.isBlank().not()
    fun isEqualUsernameAndSwitchUsername(): Boolean = (username == switchUsername)
}
