package com.mugiwara.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "market_data")
data class MarketDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val symbol: String,
    val status: String, // e.g., "OPEN", "CLOSED"
    val lastUpdate: Long
)
