package com.mugiwara.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Strategy Entity - Trading Strategies
 */
@Entity(tableName = "strategies")
data class StrategyEntity(
    @PrimaryKey
    @ColumnInfo(name = "strategy_id")
    val strategyId: String,
    
    @ColumnInfo(name = "account_id")
    val accountId: String,
    
    @ColumnInfo(name = "name")
    val name: String,
    
    @ColumnInfo(name = "description")
    val description: String? = null,
    
    @ColumnInfo(name = "type")
    val type: String, // SCALPING, SWING, DAY_TRADING, etc.
    
    @ColumnInfo(name = "symbols")
    val symbols: String, // JSON array of symbols
    
    @ColumnInfo(name = "timeframes")
    val timeframes: String, // JSON array of timeframes
    
    @ColumnInfo(name = "risk_percent")
    val riskPercent: Double = 2.0,
    
    @ColumnInfo(name = "max_daily_loss")
    val maxDailyLoss: Double = 500.0,
    
    @ColumnInfo(name = "max_trades_per_day")
    val maxTradesPerDay: Int = 10,
    
    @ColumnInfo(name = "is_enabled")
    val isEnabled: Boolean = true,
    
    @ColumnInfo(name = "parameters")
    val parameters: String? = null, // JSON string of strategy parameters
    
    @ColumnInfo(name = "win_rate")
    val winRate: Double? = null,
    
    @ColumnInfo(name = "profit_factor")
    val profitFactor: Double? = null,
    
    @ColumnInfo(name = "total_trades")
    val totalTrades: Int = 0,
    
    @ColumnInfo(name = "total_profit")
    val totalProfit: Double = 0.0,
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)

/**
 * Signal Entity - Trading Signals
 */
@Entity(tableName = "signals")
data class SignalEntity(
    @PrimaryKey
    @ColumnInfo(name = "signal_id")
    val signalId: String,
    
    @ColumnInfo(name = "strategy_id")
    val strategyId: String,
    
    @ColumnInfo(name = "symbol")
    val symbol: String,
    
    @ColumnInfo(name = "timeframe")
    val timeframe: String,
    
    @ColumnInfo(name = "type")
    val type: String, // BUY, SELL, CLOSE
    
    @ColumnInfo(name = "strength")
    val strength: Double, // 0.0 to 1.0
    
    @ColumnInfo(name = "confidence")
    val confidence: Double, // 0.0 to 1.0
    
    @ColumnInfo(name = "entry_price")
    val entryPrice: Double,
    
    @ColumnInfo(name = "stop_loss")
    val stopLoss: Double,
    
    @ColumnInfo(name = "take_profit")
    val takeProfit: Double,
    
    @ColumnInfo(name = "risk_reward_ratio")
    val riskRewardRatio: Double,
    
    @ColumnInfo(name = "indicators")
    val indicators: String? = null, // JSON string
    
    @ColumnInfo(name = "is_executed")
    val isExecuted: Boolean = false,
    
    @ColumnInfo(name = "is_filtered")
    val isFiltered: Boolean = false,
    
    @ColumnInfo(name = "filter_reason")
    val filterReason: String? = null,
    
    @ColumnInfo(name = "expires_at")
    val expiresAt: Long,
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)

/**
 * Risk Management Entity - Risk Management Settings
 */
@Entity(tableName = "risk_management")
data class RiskManagementEntity(
    @PrimaryKey
    @ColumnInfo(name = "risk_id")
    val riskId: String,
    
    @ColumnInfo(name = "account_id")
    val accountId: String,
    
    @ColumnInfo(name = "daily_loss_limit")
    val dailyLossLimit: Double,
    
    @ColumnInfo(name = "max_open_positions")
    val maxOpenPositions: Int = 5,
    
    @ColumnInfo(name = "max_correlation")
    val maxCorrelation: Double = 0.8,
    
    @ColumnInfo(name = "use_martingale")
    val useMartingale: Boolean = false,
    
    @ColumnInfo(name = "martingale_multiplier")
    val martingaleMultiplier: Double = 2.0,
    
    @ColumnInfo(name = "use_kelly_criterion")
    val useKellyCriterion: Boolean = true,
    
    @ColumnInfo(name = "use_trailing_stop")
    val useTrailingStop: Boolean = true,
    
    @ColumnInfo(name = "trailing_stop_pips")
    val trailingStopPips: Int = 50,
    
    @ColumnInfo(name = "break_even_point_pips")
    val breakEvenPointPips: Int? = null,
    
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)

/**
 * Trading Session Entity - Trading Session Information
 */
@Entity(tableName = "trading_sessions")
data class TradingSessionEntity(
    @PrimaryKey
    @ColumnInfo(name = "session_id")
    val sessionId: String,
    
    @ColumnInfo(name = "account_id")
    val accountId: String,
    
    @ColumnInfo(name = "session_name")
    val sessionName: String, // US, EU, ASIA, etc.
    
    @ColumnInfo(name = "start_time")
    val startTime: String, // HH:MM UTC
    
    @ColumnInfo(name = "end_time")
    val endTime: String, // HH:MM UTC
    
    @ColumnInfo(name = "is_active")
    val isActive: Boolean = true,
    
    @ColumnInfo(name = "symbols")
    val symbols: String, // JSON array of symbols active in this session
    
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)

/**
 * Notification Entity - User Notifications
 */
@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey
    @ColumnInfo(name = "notification_id")
    val notificationId: String,
    
    @ColumnInfo(name = "account_id")
    val accountId: String,
    
    @ColumnInfo(name = "type")
    val type: String, // TRADE_OPENED, TRADE_CLOSED, SIGNAL, WARNING, ERROR, INFO
    
    @ColumnInfo(name = "title")
    val title: String,
    
    @ColumnInfo(name = "message")
    val message: String,
    
    @ColumnInfo(name = "priority")
    val priority: Int = 0, // 0=LOW, 1=MEDIUM, 2=HIGH, 3=CRITICAL
    
    @ColumnInfo(name = "is_read")
    val isRead: Boolean = false,
    
    @ColumnInfo(name = "action_url")
    val actionUrl: String? = null,
    
    @ColumnInfo(name = "metadata")
    val metadata: String? = null, // JSON string
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    
    @ColumnInfo(name = "read_at")
    val readAt: Long? = null
)

/**
 * System Log Entity - Application Logs
 */
@Entity(tableName = "system_logs")
data class SystemLogEntity(
    @PrimaryKey
    @ColumnInfo(name = "log_id")
    val logId: String,
    
    @ColumnInfo(name = "level")
    val level: String, // DEBUG, INFO, WARNING, ERROR, CRITICAL
    
    @ColumnInfo(name = "tag")
    val tag: String,
    
    @ColumnInfo(name = "message")
    val message: String,
    
    @ColumnInfo(name = "stacktrace")
    val stacktrace: String? = null,
    
    @ColumnInfo(name = "account_id")
    val accountId: String? = null,
    
    @ColumnInfo(name = "user_id")
    val userId: String? = null,
    
    @ColumnInfo(name = "metadata")
    val metadata: String? = null, // JSON string
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
