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
    
    @ColumnInfo(name = "group")
    val group: String, // FOREX, CRYPTO, INDICES, STOCKS, COMMODITIES
    
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

/**
 * Candle Entity - OHLC Candles for Technical Analysis
 */
@Entity(tableName = "candles")
data class CandleEntity(
    @PrimaryKey
    @ColumnInfo(name = "candle_id")
    val candleId: String, // symbol_timeframe_timestamp
    
    @ColumnInfo(name = "symbol")
    val symbol: String,
    
    @ColumnInfo(name = "timeframe")
    val timeframe: String, // M1, M5, M15, M30, H1, H4, D1, W1, MN1
    
    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
    
    @ColumnInfo(name = "open")
    val open: Double,
    
    @ColumnInfo(name = "high")
    val high: Double,
    
    @ColumnInfo(name = "low")
    val low: Double,
    
    @ColumnInfo(name = "close")
    val close: Double,
    
    @ColumnInfo(name = "volume")
    val volume: Long = 0,
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Tick Entity - Real-time Price Ticks
 */
@Entity(tableName = "ticks")
data class TickEntity(
    @PrimaryKey
    @ColumnInfo(name = "tick_id")
    val tickId: String,
    
    @ColumnInfo(name = "symbol")
    val symbol: String,
    
    @ColumnInfo(name = "bid")
    val bid: Double,
    
    @ColumnInfo(name = "ask")
    val ask: Double,
    
    @ColumnInfo(name = "volume")
    val volume: Long? = null,
    
    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Market Data Entity - General Market Information
 */
@Entity(tableName = "market_data")
data class MarketDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "data_id")
    val dataId: String,
    
    @ColumnInfo(name = "symbol")
    val symbol: String,
    
    @ColumnInfo(name = "high_24h")
    val high24h: Double? = null,
    
    @ColumnInfo(name = "low_24h")
    val low24h: Double? = null,
    
    @ColumnInfo(name = "volume_24h")
    val volume24h: Long? = null,
    
    @ColumnInfo(name = "change_24h")
    val change24h: Double? = null,
    
    @ColumnInfo(name = "change_24h_percent")
    val change24hPercent: Double? = null,
    
    @ColumnInfo(name = "market_sentiment")
    val marketSentiment: String? = null, // BULLISH, BEARISH, NEUTRAL
    
    @ColumnInfo(name = "news_events")
    val newsEvents: String? = null, // JSON string of upcoming news
    
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)
