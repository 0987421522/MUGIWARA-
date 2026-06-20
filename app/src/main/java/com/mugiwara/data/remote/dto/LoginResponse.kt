package com.mugiwara.data.remote.dto

data class LoginResponse(
    val token: String,
    val expiresIn: Long,
    val accountId: String
)
