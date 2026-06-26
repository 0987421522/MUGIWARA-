package com.mugiwara.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Login request to MT5 API
 */
data class Mt5LoginRequest(
    @SerializedName("login")
    val login: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("server")
    val server: String? = null,
    @SerializedName("device_id")
    val deviceId: String? = null
)

/**
 * Open trade request
 */
data class Mt5OpenTradeRequest(
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("type")
    val type: String, // BUY or SELL
    @SerializedName("volume")
    val volume: Double,
    @SerializedName("price")
    val price: Double? = null, // null for market order
    @SerializedName("sl")
    val stopLoss: Double? = null,
    @SerializedName("tp")
    val takeProfit: Double? = null,
    @SerializedName("comment")
    val comment: String? = null,
    @SerializedName("magic")
    val magic: Int? = null,
    @SerializedName("deviation")
    val deviation: Int = 5,
    @SerializedName("order_type")
    val orderType: String = "MARKET" // MARKET or PENDING
)

/**
 * Close trade request
 */
data class Mt5CloseTradeRequest(
    @SerializedName("ticket")
    val ticket: String,
    @SerializedName("volume")
    val volume: Double,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("deviation")
    val deviation: Int = 5,
    @SerializedName("comment")
    val comment: String? = null
)

/**
 * Modify trade request (change SL/TP)
 */
data class Mt5ModifyTradeRequest(
    @SerializedName("ticket")
    val ticket: String,
    @SerializedName("sl")
    val stopLoss: Double? = null,
    @SerializedName("tp")
    val takeProfit: Double? = null,
    @SerializedName("comment")
    val comment: String? = null
)
