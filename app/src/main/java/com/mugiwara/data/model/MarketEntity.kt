package com.mugiwara.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "markets")
data class MarketEntity(
    @PrimaryKey
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
    val volume24h: Double,
    val isOpen: Boolean,
    val openTime: Long,
    val closeTime: Long,
    val timezone: String,
    val updatedAt: Long
)
