package com.mugiwara.data.remote.dto

data class TradeResponse(
    val ticket: String,
    val symbol: String,
    val type: String,
    val volume: Double,
    val openPrice: Double,
    val currentPrice: Double,
    val sl: Double,
    val tp: Double,
    val profit: Double,
    val swap: Double,
    val commission: Double,
    val openTime: Long,
    val comment: String?,
    val magic: Int
)
