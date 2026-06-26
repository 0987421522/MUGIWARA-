package com.mugiwara.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * User Account Entity - Personal user information
 */
@Entity(tableName = "user_accounts")
data class UserAccountEntity(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    val userId: String,
    
    @ColumnInfo(name = "username")
    val username: String,
    
    @ColumnInfo(name = "email")
    val email: String,
    
    @ColumnInfo(name = "password_hash")
    val passwordHash: String,
    
    @ColumnInfo(name = "is_verified")
    val isVerified: Boolean = false,
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis(),
    
    @ColumnInfo(name = "last_login")
    val lastLogin: Long? = null
)

/**
 * Trading Account Entity - MT5 Account Information
 */
@Entity(tableName = "trading_accounts")
data class TradingAccountEntity(
    @PrimaryKey
    @ColumnInfo(name = "account_id")
    val accountId: String,
    
    @ColumnInfo(name = "user_id")
    val userId: String,
    
    @ColumnInfo(name = "account_number")
    val accountNumber: String,
    
    @ColumnInfo(name = "account_type")
    val accountType: String, // REAL, DEMO, MICRO
    
    @ColumnInfo(name = "balance")
    val balance: Double,
    
    @ColumnInfo(name = "equity")
    val equity: Double,
    
    @ColumnInfo(name = "free_margin")
    val freeMargin: Double,
    
    @ColumnInfo(name = "margin_level")
    val marginLevel: Double,
    
    @ColumnInfo(name = "leverage")
    val leverage: Int,
    
    @ColumnInfo(name = "currency")
    val currency: String,
    
    @ColumnInfo(name = "company")
    val company: String? = null,
    
    @ColumnInfo(name = "is_live")
    val isLive: Boolean,
    
    @ColumnInfo(name = "is_connected")
    val isConnected: Boolean = false,
    
    @ColumnInfo(name = "mt5_token")
    val mt5Token: String? = null,
    
    @ColumnInfo(name = "last_sync")
    val lastSync: Long = System.currentTimeMillis(),
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)

/**
 * Order Entity - Pending/Executed Orders
 */
@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey
    @ColumnInfo(name = "order_id")
    val orderId: String,
    
    @ColumnInfo(name = "account_id")
    val accountId: String,
    
    @ColumnInfo(name = "symbol")
    val symbol: String,
    
    @ColumnInfo(name = "type")
    val type: String, // BUY, SELL
    
    @ColumnInfo(name = "order_type")
    val orderType: String, // MARKET, LIMIT, STOP, BUY_STOP, SELL_STOP
    
    @ColumnInfo(name = "volume")
    val volume: Double,
    
    @ColumnInfo(name = "price")
    val price: Double? = null,
    
    @ColumnInfo(name = "stop_loss")
    val stopLoss: Double? = null,
    
    @ColumnInfo(name = "take_profit")
    val takeProfit: Double? = null,
    
    @ColumnInfo(name = "status")
    val status: String, // PENDING, ACTIVE, EXECUTED, CANCELLED, REJECTED
    
    @ColumnInfo(name = "magic_number")
    val magicNumber: Int? = null,
    
    @ColumnInfo(name = "comment")
    val comment: String? = null,
    
    @ColumnInfo(name = "deviation")
    val deviation: Int = 5,
    
    @ColumnInfo(name = "execution_price")
    val executionPrice: Double? = null,
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    
    @ColumnInfo(name = "executed_at")
    val executedAt: Long? = null,
    
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)

/**
 * Position Entity - Open Trading Positions
 */
@Entity(tableName = "positions")
data class PositionEntity(
    @PrimaryKey
    @ColumnInfo(name = "position_id")
    val positionId: String,
    
    @ColumnInfo(name = "account_id")
    val accountId: String,
    
    @ColumnInfo(name = "symbol")
    val symbol: String,
    
    @ColumnInfo(name = "type")
    val type: String, // BUY, SELL
    
    @ColumnInfo(name = "volume")
    val volume: Double,
    
    @ColumnInfo(name = "entry_price")
    val entryPrice: Double,
    
    @ColumnInfo(name = "current_price")
    val currentPrice: Double,
    
    @ColumnInfo(name = "stop_loss")
    val stopLoss: Double? = null,
    
    @ColumnInfo(name = "take_profit")
    val takeProfit: Double? = null,
    
    @ColumnInfo(name = "trailing_stop")
    val trailingStop: Double? = null,
    
    @ColumnInfo(name = "profit_loss")
    val profitLoss: Double = 0.0,
    
    @ColumnInfo(name = "profit_loss_percent")
    val profitLossPercent: Double = 0.0,
    
    @ColumnInfo(name = "commission")
    val commission: Double = 0.0,
    
    @ColumnInfo(name = "swap")
    val swap: Double = 0.0,
    
    @ColumnInfo(name = "magic_number")
    val magicNumber: Int? = null,
    
    @ColumnInfo(name = "comment")
    val comment: String? = null,
    
    @ColumnInfo(name = "open_time")
    val openTime: Long,
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)

/**
 * Deal Entity - Closed Trades
 */
@Entity(tableName = "deals")
data class DealEntity(
    @PrimaryKey
    @ColumnInfo(name = "deal_id")
    val dealId: String,
    
    @ColumnInfo(name = "account_id")
    val accountId: String,
    
    @ColumnInfo(name = "symbol")
    val symbol: String,
    
    @ColumnInfo(name = "type")
    val type: String, // BUY, SELL
    
    @ColumnInfo(name = "volume")
    val volume: Double,
    
    @ColumnInfo(name = "entry_price")
    val entryPrice: Double,
    
    @ColumnInfo(name = "exit_price")
    val exitPrice: Double,
    
    @ColumnInfo(name = "profit_loss")
    val profitLoss: Double,
    
    @ColumnInfo(name = "profit_loss_percent")
    val profitLossPercent: Double,
    
    @ColumnInfo(name = "commission")
    val commission: Double = 0.0,
    
    @ColumnInfo(name = "swap")
    val swap: Double = 0.0,
    
    @ColumnInfo(name = "magic_number")
    val magicNumber: Int? = null,
    
    @ColumnInfo(name = "comment")
    val comment: String? = null,
    
    @ColumnInfo(name = "open_time")
    val openTime: Long,
    
    @ColumnInfo(name = "close_time")
    val closeTime: Long,
    
    @ColumnInfo(name = "duration_seconds")
    val durationSeconds: Long = 0,
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)
