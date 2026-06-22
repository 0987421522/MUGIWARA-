package com.mugiwara.di

import android.content.Context
import androidx.room.Room
import com.mugiwara.data.local.*
import com.mugiwara.data.remote.MT5ApiService
import com.mugiwara.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MugiwaraDatabase {
        return Room.databaseBuilder(
            context,
            MugiwaraDatabase::class.java,
            "mugiwara_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides fun provideAccountDao(db: MugiwaraDatabase): AccountDao = db.accountDao()
    @Provides fun provideTradeDao(db: MugiwaraDatabase): TradeDao = db.tradeDao()
    @Provides fun provideMarketDao(db: MugiwaraDatabase): MarketDao = db.marketDao()
    @Provides fun provideSignalDao(db: MugiwaraDatabase): SignalDao = db.signalDao()
    @Provides fun provideSettingsDao(db: MugiwaraDatabase): SettingsDao = db.settingsDao()

    @Provides @Singleton
    fun provideAccountRepository(dao: AccountDao, api: MT5ApiService): AccountRepository = AccountRepository(dao, api)

    @Provides @Singleton
    fun provideTradeRepository(dao: TradeDao, api: MT5ApiService): TradeRepository = TradeRepository(dao, api)

    @Provides @Singleton
    fun provideMarketRepository(dao: MarketDao, api: MT5ApiService): MarketRepository = MarketRepository(dao, api)

    @Provides @Singleton
    fun provideSignalRepository(dao: SignalDao): SignalRepository = SignalRepository(dao)

    @Provides @Singleton
    fun provideSettingsRepository(dao: SettingsDao): SettingsRepository = SettingsRepository(dao)
}
