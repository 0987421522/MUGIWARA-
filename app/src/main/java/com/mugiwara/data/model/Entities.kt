package com.mugiwara.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data model for Account Entity (DTO)
 */
data class ApiAccountDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("account_number")
    val accountNumber: String,
    @SerializedName("account_type")
    val accountType: String,
    @SerializedName("balance")
    val balance: Double,
    @SerializedName("equity")
    val equity: Double,
    @SerializedName("free_margin")
    val freeMargin: Double,
    @SerializedName("margin_level")
    val marginLevel: Double,
    @SerializedName("leverage")
    val leverage: Int,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("is_live")
    val isLive: Boolean,
    @SerializedName("is_connected")
    val isConnected: Boolean = false,
    @SerializedName("mt_token")
    val mtToken: String? = null,
    @SerializedName("last_updated")
    val lastUpdated: Long = System.currentTimeMillis()
)

/**
 * Data model for Trade Entity (DTO)
 */
data class TradeEntity(
    @SerializedName("id")
    val id: String,
    @SerializedName("account_id")
    val accountId: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("type")
    val type: String, // BUY or SELL
    @SerializedName("entry_price")
    val entryPrice: Double,
    @SerializedName("exit_price")
    val exitPrice: Double? = null,
    @SerializedName("current_price")
    val currentPrice: Double,
    @SerializedName("lot_size")
    val lotSize: Double,
    @SerializedName("stop_loss")
    val stopLoss: Double,
    @SerializedName("take_profit")
    val takeProfit: Double,
    @SerializedName("trailing_stop")
    val trailingStop: Double? = null,
    @SerializedName("commission")
    val commission: Double = 0.0,
    @SerializedName("swap")
    val swap: Double = 0.0,
    @SerializedName("profit")
    val profit: Double = 0.0,
    @SerializedName("status")
    val status: String, // ACTIVE, CLOSED, PENDING
    @SerializedName("open_time")
    val openTime: Long,
    @SerializedName("close_time")
    val closeTime: Long? = null,
    @SerializedName("created_at")
    val createdAt: Long = System.currentTimeMillis(),
    @SerializedName("updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)

/**
 * Data model for Market Data Entity (DTO)
 */
data class MarketEntity(
    @SerializedName("id")
    val id: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("timeframe")
    val timeframe: String,
    @SerializedName("open")
    val open: Double,
    @SerializedName("high")
    val high: Double,
    @SerializedName("low")
    val low: Double,
    @SerializedName("close")
    val close: Double,
    @SerializedName("volume")
    val volume: Long,
    @SerializedName("timestamp")
    val timestamp: Long
)

/**
 * Data model for Signal Entity (DTO)
 */
data class SignalEntity(
    @SerializedName("id")
    val id: String,
    @SerializedName("market_id")
    val marketId: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("type")
    val type: String, // BUY or SELL
    @SerializedName("direction")
    val direction: String, // BUY or SELL
    @SerializedName("strength")
    val strength: Double, // 0.0 to 1.0
    @SerializedName("confidence")
    val confidence: Double, // 0.0 to 1.0
    @SerializedName("entry_price")
    val entryPrice: Double,
    @SerializedName("stop_loss")
    val stopLoss: Double,
    @SerializedName("take_profit")
    val takeProfit: Double,
    @SerializedName("risk_reward_ratio")
    val riskRewardRatio: Double,
    @SerializedName("indicators")
    val indicators: String, // JSON string of indicator values
    @SerializedName("time_frame")
    val timeFrame: String,
    @SerializedName("is_executed")
    val isExecuted: Boolean = false,
    @SerializedName("is_filtered")
    val isFiltered: Boolean = false,
    @SerializedName("reason")
    val reason: String? = null,
    @SerializedName("created_at")
    val createdAt: Long = System.currentTimeMillis(),
    @SerializedName("expires_at")
    val expiresAt: Long = System.currentTimeMillis() + 3600000
)

/**
 * Data model for Settings Entity (DTO)
 */
data class SettingsEntity(
    @SerializedName("id")
    val id: String = "settings",
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
    val trailingStopPips: Int = 50
)
