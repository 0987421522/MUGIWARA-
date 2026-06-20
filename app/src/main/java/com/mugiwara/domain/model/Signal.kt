package com.mugiwara.domain.model

data class Signal(
    val id: String,
    val marketId: String,
    val symbol: String,
    val direction: SignalDirection,
    val strength: Double,
    val confidence: Double,
    val entryPrice: Double,
    val stopLoss: Double,
    val takeProfit: Double,
    val riskRewardRatio: Double,
    val timeFrame: String,
    val isExecuted: Boolean,
    val isFiltered: Boolean
)

enum class SignalDirection {
    BUY, SELL, NEUTRAL
}
