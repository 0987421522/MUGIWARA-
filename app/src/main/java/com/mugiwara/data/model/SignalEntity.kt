package com.mugiwara.data.model

import com.google.gson.annotations.SerializedName

/**
 * API DTO for Signal (renamed from SignalEntity to avoid collision with Room entity)
 */
data class ApiSignalDto(
    @SerializedName("signal_id")
    val signalId: String,
    @SerializedName("strategy_id")
    val strategyId: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("timeframe")
    val timeframe: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("strength")
    val strength: Double,
    @SerializedName("confidence")
    val confidence: Double,
    @SerializedName("entry_price")
    val entryPrice: Double,
    @SerializedName("stop_loss")
    val stopLoss: Double,
    @SerializedName("take_profit")
    val takeProfit: Double,
    @SerializedName("risk_reward_ratio")
    val riskRewardRatio: Double,
    @SerializedName("indicators")
    val indicators: String? = null,
    @SerializedName("is_executed")
    val isExecuted: Boolean = false,
    @SerializedName("is_filtered")
    val isFiltered: Boolean = false,
    @SerializedName("filter_reason")
    val filterReason: String? = null,
    @SerializedName("expires_at")
    val expiresAt: Long,
    @SerializedName("created_at")
    val createdAt: Long = System.currentTimeMillis(),
    @SerializedName("updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)
