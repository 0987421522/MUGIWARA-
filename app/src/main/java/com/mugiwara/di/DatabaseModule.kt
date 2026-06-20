package com.mugiwara.di

import android.content.Context
import androidx.room.Room
import com.mugiwara.data.local.*
import com.mugiwara.utils.Constants
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
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MugiwaraDatabase {
        return Room.databaseBuilder(
            context,
            MugiwaraDatabase::class.java,
            Constants.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    
    @Provides
    fun provideAccountDao(database: MugiwaraDatabase): AccountDao {
        return database.accountDao()
    }
    
    @Provides
    fun provideTradeDao(database: MugiwaraDatabase): TradeDao {
        return database.tradeDao()
    }
    
    @Provides
    fun provideMarketDao(database: MugiwaraDatabase): MarketDao {
        return database.marketDao()
    }
    
    @Provides
    fun provideSignalDao(database: MugiwaraDatabase): SignalDao {
        return database.signalDao()
    }
    
    @Provides
    fun provideSettingsDao(database: MugiwaraDatabase): SettingsDao {
        return database.settingsDao()
    }
}
