package com.mugiwara.data.remote.dto

data class AccountInfoResponse(
    val login: String,
    val name: String,
    val server: String,
    val balance: Double,
    val equity: Double,
    val margin: Double,
    val freeMargin: Double,
    val marginLevel: Double,
    val leverage: Int,
    val currency: String
)
