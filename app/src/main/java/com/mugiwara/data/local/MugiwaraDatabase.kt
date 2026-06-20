package com.mugiwara.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mugiwara.data.model.AccountEntity
import com.mugiwara.data.model.MarketEntity
import com.mugiwara.data.model.SettingsEntity
import com.mugiwara.data.model.SignalEntity
import com.mugiwara.data.model.TradeEntity

@Database(
    entities = [
        AccountEntity::class,
        TradeEntity::class,
        MarketEntity::class,
        SignalEntity::class,
        SettingsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MugiwaraDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun tradeDao(): TradeDao
    abstract fun marketDao(): MarketDao
    abstract fun signalDao(): SignalDao
    abstract fun settingsDao(): SettingsDao
}
