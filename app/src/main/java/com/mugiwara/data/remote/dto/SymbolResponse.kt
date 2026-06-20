package com.mugiwara.data.remote.dto

data class SymbolResponse(
    val symbol: String,
    val name: String,
    val category: String,
    val digits: Int,
    val point: Double,
    val contractSize: Double,
    val minLot: Double,
    val maxLot: Double,
    val lotStep: Double,
    val swapLong: Double,
    val swapShort: Double,
    val tradeAllowed: Boolean
)
