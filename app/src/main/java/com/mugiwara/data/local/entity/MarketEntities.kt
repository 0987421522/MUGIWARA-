package com.mugiwara.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Symbol Entity - Available Trading Symbols
 */
@Entity(tableName = "symbols")
data class SymbolEntity(
    @PrimaryKey
    @ColumnInfo(name = "symbol")
    val symbol: String,
    
    @ColumnInfo(name = "name")
    val name: String,
    
    @ColumnInfo(name = "symbol_group")
    val symbolGroup: String, // FOREX, CRYPTO, INDICES, STOCKS, COMMODITIES
    
    @ColumnInfo(name = "bid")
    val bid: Double = 0.0,
    
    @ColumnInfo(name = "ask")
    val ask: Double = 0.0,
    
    @ColumnInfo(name = "spread")
    val spread: Double = 0.0,
    
    @ColumnInfo(name = "point")
    val point: Double = 0.00001,
    
    @ColumnInfo(name = "digits")
    val digits: Int = 5,
    
    @ColumnInfo(name = "min_volume")
    val minVolume: Double = 0.01,
    
    @ColumnInfo(name = "max_volume")
    val maxVolume: Double = 100.0,
    
    @ColumnInfo(name = "volume_step")
    val volumeStep: Double = 0.01,
    
    @ColumnInfo(name = "session_open")
    val sessionOpen: String? = null, // HH:MM
    
    @ColumnInfo(name = "session_close")
    val sessionClose: String? = null, // HH:MM
    
    @ColumnInfo(name = "is_tradable")
    val isTradable: Boolean = true,
    
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)
