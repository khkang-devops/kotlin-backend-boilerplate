package com.test.app.api.user.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "로그인 요청")
data class LoginRequest(
    @Schema(description = "사용자 ID")
    val username: String,

    @Schema(description = "비밀번호")
    val password: String
)

data class LoginResponse(
    val username: String,
    val userDesc: String,
    val partnerName: String,
    val deptNm: String? = "",
    val roles: List<String>,
    val biztps: List<String>? = emptyList(),
    val passwordReset: Boolean = false,
    val accessToken: String,
    val refreshToken: String?,
    val groups: List<String>? = listOf(),
)

data class SwitchUserRequest(
    val username: String,
    val switchUsername: String,
)

data class SwitchUserResponse(
    val username: String,
    val userDesc: String,
    val partnerName: String,
    val deptNm: String? = "",
    val roles: List<String>,
    val biztps: List<String>? = emptyList(),
    val passwordReset: Boolean = false,
    val groups: List<String>? = listOf(),
    val accessToken: String
)

@Schema(description = "엑세스 토큰 요청")
data class AccessTokenRequest(
    @Schema(description = "사용자 ID")
    val username: String,

    @Schema(description = "사용자 명")
    val userDesc: String,

    @Schema(description = "역활 셋")
    val roles: List<String>,

    @Schema(description = "협력사 명")
    val partnerName: String,

    @Schema(description = "사용자 그룹")
    val groups: List<String>,
)

data class LdapUser(
    val cn: String,
    val company: String,
    val companyCode: String,
    val department: String,
    val departmentCode: String,
    val description: String,
    val displayName: String,
    val mail: String,
    val userPrincipalName: String,
)