package com.mugiwara.data.remote.dto

data class OpenTradeRequest(
    val symbol: String,
    val type: String,
    val volume: Double,
    val price: Double,
    val sl: Double,
    val tp: Double,
    val comment: String?,
    val magic: Int
)
