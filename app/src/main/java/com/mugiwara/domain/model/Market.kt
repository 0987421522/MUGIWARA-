package com.mugiwara.domain.model

data class Market(
    val id: String,
    val name: String,
    val symbol: String,
    val category: MarketCategory,
    val price: Double,
    val bid: Double,
    val ask: Double,
    val spread: Double,
    val change: Double,
    val changePercent: Double,
    val high24h: Double,
    val low24h: Double,
    val volume24h: Double,
    val isOpen: Boolean
)

enum class MarketCategory {
    FOREX, GOLD, SILVER, CRYPTO, INDICES, STOCKS
}
