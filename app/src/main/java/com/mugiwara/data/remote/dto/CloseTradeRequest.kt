package com.mugiwara.data.remote.dto

data class CloseTradeRequest(
    val ticket: String,
    val volume: Double,
    val price: Double
)
