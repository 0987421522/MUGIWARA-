package com.mugiwara.domain.model

data class Market(
    val id: String,
    val name: String,
    val symbol: String,
    val category: String,
    val price: Double,
    val bid: Double,
    val ask: Double,
    val spread: Double,
    val change: Double,
    val changePercent: Double,
    val high24h: Double,
    val low24h: Double,
    val volume24h: Long,
    val isOpen: Boolean,
    val openTime: Long,
    val closeTime: Long,
    val timezone: String,
    val updatedAt: Long
)
