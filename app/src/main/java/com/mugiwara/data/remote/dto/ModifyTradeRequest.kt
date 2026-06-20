package com.mugiwara.data.remote.dto

data class ModifyTradeRequest(
    val ticket: String,
    val sl: Double?,
    val tp: Double?
)
