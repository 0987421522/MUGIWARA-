package com.mugiwara.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Login response from MT5 API
 */
data class Mt5LoginResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("token")
    val token: String,
    @SerializedName("account_id")
    val accountId: String,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("expires_in")
    val expiresIn: Long? = null
)

/**
 * Logout response from MT5 API
 */
data class Mt5LogoutResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String? = null
)

/**
 * Account information response
 */
data class Mt5AccountResponse(
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
    @SerializedName("company")
    val company: String? = null,
    @SerializedName("is_live")
    val isLive: Boolean,
    @SerializedName("name")
    val name: String? = null
)

/**
 * Quote/price response
 */
data class Mt5QuoteResponse(
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("bid")
    val bid: Double,
    @SerializedName("ask")
    val ask: Double,
    @SerializedName("last")
    val last: Double,
    @SerializedName("volume")
    val volume: Long,
    @SerializedName("time")
    val time: Long,
    @SerializedName("spread")
    val spread: Double? = null,
    @SerializedName("high")
    val high: Double? = null,
    @SerializedName("low")
    val low: Double? = null
)

/**
 * Market history/candles response
 */
data class Mt5HistoryResponse(
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("timeframe")
    val timeframe: String,
    @SerializedName("candles")
    val candles: List<Mt5Candle>
)

/**
 * Individual OHLC candle
 */
data class Mt5Candle(
    @SerializedName("time")
    val time: Long,
    @SerializedName("open")
    val open: Double,
    @SerializedName("high")
    val high: Double,
    @SerializedName("low")
    val low: Double,
    @SerializedName("close")
    val close: Double,
    @SerializedName("volume")
    val volume: Long
)

/**
 * Trade execution response
 */
data class Mt5TradeResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("ticket")
    val ticket: String? = null,
    @SerializedName("order_id")
    val orderId: String? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("volume")
    val volume: Double? = null,
    @SerializedName("commission")
    val commission: Double? = null,
    @SerializedName("swap")
    val swap: Double? = null,
    @SerializedName("profit")
    val profit: Double? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("error_code")
    val errorCode: Int? = null
)

/**
 * Open positions response
 */
data class Mt5PositionsResponse(
    @SerializedName("positions")
    val positions: List<Mt5Position>,
    @SerializedName("count")
    val count: Int,
    @SerializedName("total_profit")
    val totalProfit: Double? = null
)

/**
 * Individual position information
 */
data class Mt5Position(
    @SerializedName("ticket")
    val ticket: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("type")
    val type: String, // BUY or SELL
    @SerializedName("volume")
    val volume: Double,
    @SerializedName("entry_price")
    val entryPrice: Double,
    @SerializedName("current_price")
    val currentPrice: Double,
    @SerializedName("stop_loss")
    val stopLoss: Double? = null,
    @SerializedName("take_profit")
    val takeProfit: Double? = null,
    @SerializedName("profit")
    val profit: Double,
    @SerializedName("commission")
    val commission: Double? = null,
    @SerializedName("swap")
    val swap: Double? = null,
    @SerializedName("open_time")
    val openTime: Long,
    @SerializedName("magic")
    val magic: Int? = null,
    @SerializedName("comment")
    val comment: String? = null
)

/**
 * Trade history response
 */
data class Mt5HistoryTradesResponse(
    @SerializedName("trades")
    val trades: List<Mt5HistoryTrade>,
    @SerializedName("count")
    val count: Int
)

/**
 * Individual history trade information
 */
data class Mt5HistoryTrade(
    @SerializedName("ticket")
    val ticket: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("type")
    val type: String, // BUY or SELL
    @SerializedName("volume")
    val volume: Double,
    @SerializedName("entry_price")
    val entryPrice: Double,
    @SerializedName("exit_price")
    val exitPrice: Double,
    @SerializedName("profit")
    val profit: Double,
    @SerializedName("commission")
    val commission: Double? = null,
    @SerializedName("swap")
    val swap: Double? = null,
    @SerializedName("open_time")
    val openTime: Long,
    @SerializedName("close_time")
    val closeTime: Long,
    @SerializedName("magic")
    val magic: Int? = null,
    @SerializedName("comment")
    val comment: String? = null
)

/**
 * Server time response
 */
data class Mt5ServerTimeResponse(
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("server_time")
    val serverTime: String? = null
)
