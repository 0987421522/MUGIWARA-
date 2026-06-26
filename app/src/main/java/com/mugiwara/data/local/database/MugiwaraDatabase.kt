package com.mugiwara.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mugiwara.data.local.dao.*
import com.mugiwara.data.local.entity.*

/**
 * Main Room Database for MUGIWARA Trading System
 * Contains all entities and DAOs for persistent data storage
 */
@Database(
    entities = [
        UserAccountEntity::class,
        TradingAccountEntity::class,
        OrderEntity::class,
        PositionEntity::class,
        DealEntity::class,
        SymbolEntity::class,
        CandleEntity::class,
        TickEntity::class,
        StrategyEntity::class,
        SignalEntity::class,
        RiskManagementEntity::class,
        MarketDataEntity::class,
        TradingSessionEntity::class,
        NotificationEntity::class,
        SystemLogEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class MugiwaraDatabase : RoomDatabase() {
    
    abstract fun userAccountDao(): UserAccountDao
    abstract fun tradingAccountDao(): TradingAccountDao
    abstract fun orderDao(): OrderDao
    abstract fun positionDao(): PositionDao
    abstract fun dealDao(): DealDao
    abstract fun symbolDao(): SymbolDao
    abstract fun candleDao(): CandleDao
    abstract fun tickDao(): TickDao
    abstract fun strategyDao(): StrategyDao
    abstract fun signalDao(): SignalDao
    abstract fun riskManagementDao(): RiskManagementDao
    abstract fun marketDataDao(): MarketDataDao
    abstract fun tradingSessionDao(): TradingSessionDao
    abstract fun notificationDao(): NotificationDao
    abstract fun systemLogDao(): SystemLogDao
}
