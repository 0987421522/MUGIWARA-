package com.mugiwara.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Domain model for Settings
 */
data class Settings(
    @SerializedName("default_risk_percent")
    val defaultRiskPercent: Double = 2.0,
    
    @SerializedName("max_daily_loss")
    val maxDailyLoss: Double = 500.0,
    
    @SerializedName("max_trades_per_day")
    val maxTradesPerDay: Int = 10,
    
    @SerializedName("auto_trading_enabled")
    val autoTradingEnabled: Boolean = false,
    
    @SerializedName("trailing_stop_enabled")
    val trailingStopEnabled: Boolean = true,
    
    @SerializedName("trailing_stop_pips")
    val trailingStopPips: Int = 50,
    
    @SerializedName("use_martingale")
    val useMartingale: Boolean = false,
    
    @SerializedName("martingale_multiplier")
    val martingaleMultiplier: Double = 2.0,
    
    @SerializedName("use_kelly_criterion")
    val useKellyCriterion: Boolean = true,
    
    @SerializedName("minimum_confidence_threshold")
    val minimumConfidenceThreshold: Double = 0.6,
    
    @SerializedName("news_trading_enabled")
    val newsTradingEnabled: Boolean = false,
    
    @SerializedName("session_filter")
    val sessionFilter: String = "ALL" // US, EU, ASIA, ALL
)

/**
 * Domain model for Account
 */
data class Account(
    val id: String,
    val number: String,
    val type: String,
    val balance: Double,
    val equity: Double,
    val freeMargin: Double,
    val marginLevel: Double,
    val leverage: Int,
    val currency: String,
    val isLive: Boolean,
    val isConnected: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis()
)

/**
 * Domain model for Trade
 */
data class Trade(
    val id: String,
    val accountId: String,
    val symbol: String,
    val type: String, // BUY or SELL
    val volume: Double,
    val entryPrice: Double,
    val exitPrice: Double? = null,
    val currentPrice: Double,
    val stopLoss: Double,
    val takeProfit: Double,
    val profit: Double,
    val commission: Double = 0.0,
    val swap: Double = 0.0,
    val status: String, // ACTIVE, CLOSED, PENDING
    val openTime: Long,
    val closeTime: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

/**
 * Domain model for Market Data
 */
data class MarketData(
    val id: String,
    val symbol: String,
    val timeframe: String,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Long,
    val timestamp: Long
)

/**
 * Domain model for Signal
 */
data class Signal(
    val id: String,
    val symbol: String,
    val type: String, // BUY or SELL
    val strength: Double, // 0.0 to 1.0
    val confidence: Double, // 0.0 to 1.0
    val entryPrice: Double,
    val stopLoss: Double,
    val takeProfit: Double,
    val riskRewardRatio: Double,
    val indicators: Map<String, Double>,
    val timeFrame: String,
    val isExecuted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val expiresAt: Long = System.currentTimeMillis() + 3600000
)
