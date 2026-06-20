package com.mugiwara.data.remote.dto

data class LoginRequest(
    val server: String,
    val login: String,
    val password: String
)
