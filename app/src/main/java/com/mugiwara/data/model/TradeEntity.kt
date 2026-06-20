package com.mugiwara.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trades")
data class TradeEntity(
    @PrimaryKey
    val id: String,
    val accountId: String,
    val symbol: String,
    val type: String,
    val entryPrice: Double,
    val exitPrice: Double?,
    val currentPrice: Double,
    val lotSize: Double,
    val stopLoss: Double,
    val takeProfit: Double,
    val trailingStop: Double?,
    val commission: Double,
    val swap: Double,
    val profit: Double,
    val status: String,
    val openTime: Long,
    val closeTime: Long?,
    val createdAt: Long,
    val updatedAt: Long
)
