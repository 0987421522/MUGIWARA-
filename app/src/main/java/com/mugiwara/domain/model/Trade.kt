package com.mugiwara.domain.model

data class Trade(
    val id: String,
    val accountId: String,
    val symbol: String,
    val type: TradeType,
    val entryPrice: Double,
    val exitPrice: Double?,
    val currentPrice: Double,
    val lotSize: Double,
    val stopLoss: Double,
    val takeProfit: Double,
    val trailingStop: Double?,
    val profit: Double,
    val status: TradeStatus,
    val openTime: Long,
    val closeTime: Long?
)

enum class TradeType {
    BUY, SELL
}

enum class TradeStatus {
    ACTIVE, CLOSED, PENDING
}
