package com.mugiwara.data.remote.dto

data class TickResponse(
    val symbol: String,
    val bid: Double,
    val ask: Double,
    val last: Double,
    val volume: Long,
    val time: Long,
    val flags: Int
)
