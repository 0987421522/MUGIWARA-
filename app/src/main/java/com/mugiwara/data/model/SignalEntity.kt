package com.mugiwara.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "signals")
data class SignalEntity(
    @PrimaryKey
    val id: String,
    val marketId: String,
    val symbol: String,
    val type: String,
    val direction: String,
    val strength: Double,
    val confidence: Double,
    val entryPrice: Double,
    val stopLoss: Double,
    val takeProfit: Double,
    val riskRewardRatio: Double,
    val indicators: String,
    val timeFrame: String,
    val isExecuted: Boolean,
    val isFiltered: Boolean,
    val reason: String?,
    val createdAt: Long,
    val expiresAt: Long
)
