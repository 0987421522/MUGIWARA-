package com.mugiwara.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mugiwara.data.local.dao.* // تأكد من استيراد الـ DAOs الصحيحة
import com.mugiwara.data.local.entity.*

@Database(
    entities = [
        UserAccountEntity::class,
        TradingAccountEntity::class,
        AccountEntity::class,
        OrderEntity::class,
        PositionEntity::class,
        DealEntity::class,
        SymbolEntity::class,
        TickEntity::class,
        CandleEntity::class,
        MarketDataEntity::class,
        StrategyEntity::class,
        SignalEntity::class, // الجديدة وليس القديمة
        RiskManagementEntity::class,
        TradingSessionEntity::class,
        NotificationEntity::class,
        SystemLogEntity::class
    ],
    version = 1, // قم برفع الرقم إذا كان هناك إصدار سابق
    exportSchema = false
)
@TypeConverters(Converters::class) // تأكد من أن Converters خالية من التكرار
abstract class MugiwaraDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun orderDao(): OrderDao
    abstract fun symbolDao(): SymbolDao
    abstract fun tickDao(): TickDao
    abstract fun candleDao(): CandleDao
    abstract fun marketDataDao(): MarketDataDao
    abstract fun signalDao(): SignalDao
    // ... باقي الـ DAOs
}
