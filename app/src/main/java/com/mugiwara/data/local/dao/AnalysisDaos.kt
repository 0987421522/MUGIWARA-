package com.mugiwara.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * DAO for Strategy operations
 */
@Dao
interface StrategyDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(strategy: com.mugiwara.data.local.entity.StrategyEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(strategies: List<com.mugiwara.data.local.entity.StrategyEntity>)
    
    @Update
    suspend fun update(strategy: com.mugiwara.data.local.entity.StrategyEntity)
    
    @Delete
    suspend fun delete(strategy: com.mugiwara.data.local.entity.StrategyEntity)
    
    @Query("SELECT * FROM strategies WHERE strategy_id = :strategyId")
    suspend fun getStrategyById(strategyId: String): com.mugiwara.data.local.entity.StrategyEntity?
    
    @Query("SELECT * FROM strategies WHERE account_id = :accountId")
    fun getAccountStrategies(accountId: String): Flow<List<com.mugiwara.data.local.entity.StrategyEntity>>
    
    @Query("SELECT * FROM strategies WHERE account_id = :accountId AND is_enabled = 1")
    fun getEnabledStrategies(accountId: String): Flow<List<com.mugiwara.data.local.entity.StrategyEntity>>
    
    @Query("UPDATE strategies SET win_rate = :winRate, profit_factor = :profitFactor, total_trades = :totalTrades, total_profit = :totalProfit WHERE strategy_id = :strategyId")
    suspend fun updateStrategyStats(
        strategyId: String,
        winRate: Double,
        profitFactor: Double,
        totalTrades: Int,
        totalProfit: Double
    )
}

/**
 * DAO for Signal operations
 */
@Dao
interface SignalDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(signal: com.mugiwara.data.local.entity.SignalEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(signals: List<com.mugiwara.data.local.entity.SignalEntity>)
    
    @Update
    suspend fun update(signal: com.mugiwara.data.local.entity.SignalEntity)
    
    @Delete
    suspend fun delete(signal: com.mugiwara.data.local.entity.SignalEntity)
    
    @Query("SELECT * FROM signals WHERE signal_id = :signalId")
    suspend fun getSignalById(signalId: String): com.mugiwara.data.local.entity.SignalEntity?
    
    @Query("SELECT * FROM signals WHERE strategy_id = :strategyId ORDER BY created_at DESC")
    fun getStrategySignals(strategyId: String): Flow<List<com.mugiwara.data.local.entity.SignalEntity>>
    
    @Query("SELECT * FROM signals WHERE is_filtered = 0 AND is_executed = 0 AND expires_at > :currentTime ORDER BY confidence DESC")
    fun getActiveSignals(currentTime: Long): Flow<List<com.mugiwara.data.local.entity.SignalEntity>>
    
    @Query("SELECT * FROM signals WHERE symbol = :symbol AND is_executed = 0 ORDER BY created_at DESC LIMIT 1")
    suspend fun getLatestSignalForSymbol(symbol: String): com.mugiwara.data.local.entity.SignalEntity?
    
    @Query("SELECT * FROM signals WHERE type = :type AND is_filtered = 0 AND is_executed = 0 ORDER BY confidence DESC LIMIT :limit")
    suspend fun getSignalsByType(type: String, limit: Int = 50): List<com.mugiwara.data.local.entity.SignalEntity>
    
    @Query("UPDATE signals SET is_executed = 1, updated_at = :timestamp WHERE signal_id = :signalId")
    suspend fun markSignalAsExecuted(signalId: String, timestamp: Long)
    
    @Query("UPDATE signals SET is_filtered = 1, filter_reason = :reason, updated_at = :timestamp WHERE signal_id = :signalId")
    suspend fun markSignalAsFiltered(signalId: String, reason: String, timestamp: Long)
}

/**
 * DAO for Risk Management operations
 */
@Dao
interface RiskManagementDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(risk: com.mugiwara.data.local.entity.RiskManagementEntity)
    
    @Update
    suspend fun update(risk: com.mugiwara.data.local.entity.RiskManagementEntity)
    
    @Delete
    suspend fun delete(risk: com.mugiwara.data.local.entity.RiskManagementEntity)
    
    @Query("SELECT * FROM risk_management WHERE account_id = :accountId")
    suspend fun getRiskSettingsByAccount(accountId: String): com.mugiwara.data.local.entity.RiskManagementEntity?
    
    @Query("SELECT * FROM risk_management WHERE account_id = :accountId")
    fun getRiskSettingsFlow(accountId: String): Flow<com.mugiwara.data.local.entity.RiskManagementEntity?>
}

/**
 * DAO for Trading Session operations
 */
@Dao
interface TradingSessionDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: com.mugiwara.data.local.entity.TradingSessionEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sessions: List<com.mugiwara.data.local.entity.TradingSessionEntity>)
    
    @Update
    suspend fun update(session: com.mugiwara.data.local.entity.TradingSessionEntity)
    
    @Delete
    suspend fun delete(session: com.mugiwara.data.local.entity.TradingSessionEntity)
    
    @Query("SELECT * FROM trading_sessions WHERE session_id = :sessionId")
    suspend fun getSessionById(sessionId: String): com.mugiwara.data.local.entity.TradingSessionEntity?
    
    @Query("SELECT * FROM trading_sessions")
    fun getAllSessions(): Flow<List<com.mugiwara.data.local.entity.TradingSessionEntity>>
    
    @Query("SELECT * FROM trading_sessions WHERE is_active = 1")
    fun getActiveSessions(): Flow<List<com.mugiwara.data.local.entity.TradingSessionEntity>>
}

/**
 * DAO for Notification operations
 */
@Dao
interface NotificationDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: com.mugiwara.data.local.entity.NotificationEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notifications: List<com.mugiwara.data.local.entity.NotificationEntity>)
    
    @Update
    suspend fun update(notification: com.mugiwara.data.local.entity.NotificationEntity)
    
    @Delete
    suspend fun delete(notification: com.mugiwara.data.local.entity.NotificationEntity)
    
    @Query("SELECT * FROM notifications WHERE notification_id = :notificationId")
    suspend fun getNotificationById(notificationId: String): com.mugiwara.data.local.entity.NotificationEntity?
    
    @Query("SELECT * FROM notifications WHERE account_id = :accountId ORDER BY created_at DESC")
    fun getAccountNotifications(accountId: String): Flow<List<com.mugiwara.data.local.entity.NotificationEntity>>
    
    @Query("SELECT * FROM notifications WHERE account_id = :accountId AND is_read = 0 ORDER BY created_at DESC")
    fun getUnreadNotifications(accountId: String): Flow<List<com.mugiwara.data.local.entity.NotificationEntity>>
    
    @Query("SELECT COUNT(*) FROM notifications WHERE account_id = :accountId AND is_read = 0")
    fun getUnreadNotificationCount(accountId: String): Flow<Int>
    
    @Query("UPDATE notifications SET is_read = 1, read_at = :timestamp WHERE notification_id = :notificationId")
    suspend fun markAsRead(notificationId: String, timestamp: Long)
    
    @Query("DELETE FROM notifications WHERE account_id = :accountId AND created_at < :olderThanTimestamp")
    suspend fun deleteOldNotifications(accountId: String, olderThanTimestamp: Long)
}

/**
 * DAO for System Log operations
 */
@Dao
interface SystemLogDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: com.mugiwara.data.local.entity.SystemLogEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(logs: List<com.mugiwara.data.local.entity.SystemLogEntity>)
    
    @Query("SELECT * FROM system_logs WHERE log_id = :logId")
    suspend fun getLogById(logId: String): com.mugiwara.data.local.entity.SystemLogEntity?
    
    @Query("SELECT * FROM system_logs WHERE level = :level ORDER BY created_at DESC LIMIT :limit")
    suspend fun getLogsByLevel(level: String, limit: Int = 100): List<com.mugiwara.data.local.entity.SystemLogEntity>
    
    @Query("SELECT * FROM system_logs WHERE tag = :tag ORDER BY created_at DESC LIMIT :limit")
    suspend fun getLogsByTag(tag: String, limit: Int = 100): List<com.mugiwara.data.local.entity.SystemLogEntity>
    
    @Query("SELECT * FROM system_logs WHERE account_id = :accountId ORDER BY created_at DESC LIMIT :limit")
    suspend fun getAccountLogs(accountId: String, limit: Int = 500): List<com.mugiwara.data.local.entity.SystemLogEntity>
    
    @Query("SELECT * FROM system_logs ORDER BY created_at DESC LIMIT :limit")
    suspend fun getRecentLogs(limit: Int = 1000): List<com.mugiwara.data.local.entity.SystemLogEntity>
    
    @Query("DELETE FROM system_logs WHERE created_at < :olderThanTimestamp")
    suspend fun deleteOldLogs(olderThanTimestamp: Long)
}
