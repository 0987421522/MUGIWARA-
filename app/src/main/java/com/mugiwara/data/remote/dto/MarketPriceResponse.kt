package com.mugiwara.data.remote.dto

data class MarketPriceResponse(
    val symbol: String,
    val bid: Double,
    val ask: Double,
    val spread: Double,
    val change: Double,
    val changePercent: Double,
    val high: Double,
    val low: Double,
    val volume: Long
)
