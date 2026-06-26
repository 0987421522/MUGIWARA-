package com.mugiwara.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * DAO for Symbol operations
 */
@Dao
interface SymbolDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(symbol: com.mugiwara.data.local.entity.SymbolEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(symbols: List<com.mugiwara.data.local.entity.SymbolEntity>)
    
    @Update
    suspend fun update(symbol: com.mugiwara.data.local.entity.SymbolEntity)
    
    @Delete
    suspend fun delete(symbol: com.mugiwara.data.local.entity.SymbolEntity)
    
    @Query("SELECT * FROM symbols WHERE symbol = :symbol")
    suspend fun getSymbol(symbol: String): com.mugiwara.data.local.entity.SymbolEntity?
    
    @Query("SELECT * FROM symbols")
    fun getAllSymbols(): Flow<List<com.mugiwara.data.local.entity.SymbolEntity>>
    
    @Query("SELECT * FROM symbols WHERE is_tradable = 1")
    fun getTradableSymbols(): Flow<List<com.mugiwara.data.local.entity.SymbolEntity>>
    
    @Query("SELECT * FROM symbols WHERE group = :group")
    fun getSymbolsByGroup(group: String): Flow<List<com.mugiwara.data.local.entity.SymbolEntity>>
    
    @Query("UPDATE symbols SET bid = :bid, ask = :ask, spread = :spread, updated_at = :timestamp WHERE symbol = :symbol")
    suspend fun updateSymbolPrice(
        symbol: String,
        bid: Double,
        ask: Double,
        spread: Double,
        timestamp: Long
    )
}

/**
 * DAO for Candle operations with performance optimization
 */
@Dao
interface CandleDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(candle: com.mugiwara.data.local.entity.CandleEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(candles: List<com.mugiwara.data.local.entity.CandleEntity>)
    
    @Update
    suspend fun update(candle: com.mugiwara.data.local.entity.CandleEntity)
    
    @Delete
    suspend fun delete(candle: com.mugiwara.data.local.entity.CandleEntity)
    
    @Query("SELECT * FROM candles WHERE candle_id = :candleId")
    suspend fun getCandleById(candleId: String): com.mugiwara.data.local.entity.CandleEntity?
    
    @Query("SELECT * FROM candles WHERE symbol = :symbol AND timeframe = :timeframe ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentCandles(
        symbol: String,
        timeframe: String,
        limit: Int = 100
    ): List<com.mugiwara.data.local.entity.CandleEntity>
    
    @Query("SELECT * FROM candles WHERE symbol = :symbol AND timeframe = :timeframe AND timestamp >= :startTime ORDER BY timestamp ASC")
    suspend fun getCandlesByDateRange(
        symbol: String,
        timeframe: String,
        startTime: Long
    ): List<com.mugiwara.data.local.entity.CandleEntity>
    
    @Query("SELECT COUNT(*) FROM candles WHERE symbol = :symbol AND timeframe = :timeframe")
    suspend fun getCandleCount(symbol: String, timeframe: String): Int
    
    @Query("DELETE FROM candles WHERE symbol = :symbol AND timeframe = :timeframe AND timestamp < :olderThanTimestamp")
    suspend fun deleteOldCandles(symbol: String, timeframe: String, olderThanTimestamp: Long)
}

/**
 * DAO for Tick operations
 */
@Dao
interface TickDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tick: com.mugiwara.data.local.entity.TickEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ticks: List<com.mugiwara.data.local.entity.TickEntity>)
    
    @Update
    suspend fun update(tick: com.mugiwara.data.local.entity.TickEntity)
    
    @Delete
    suspend fun delete(tick: com.mugiwara.data.local.entity.TickEntity)
    
    @Query("SELECT * FROM ticks WHERE symbol = :symbol ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestTick(symbol: String): com.mugiwara.data.local.entity.TickEntity?
    
    @Query("SELECT * FROM ticks WHERE symbol = :symbol ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentTicks(symbol: String, limit: Int = 100): List<com.mugiwara.data.local.entity.TickEntity>
    
    @Query("SELECT * FROM ticks WHERE symbol = :symbol AND timestamp >= :startTime ORDER BY timestamp DESC")
    suspend fun getTicksByTimeRange(symbol: String, startTime: Long): List<com.mugiwara.data.local.entity.TickEntity>
    
    @Query("DELETE FROM ticks WHERE symbol = :symbol AND timestamp < :olderThanTimestamp")
    suspend fun deleteOldTicks(symbol: String, olderThanTimestamp: Long)
}

/**
 * DAO for Market Data operations
 */
@Dao
interface MarketDataDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(marketData: com.mugiwara.data.local.entity.MarketDataEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(marketDataList: List<com.mugiwara.data.local.entity.MarketDataEntity>)
    
    @Update
    suspend fun update(marketData: com.mugiwara.data.local.entity.MarketDataEntity)
    
    @Delete
    suspend fun delete(marketData: com.mugiwara.data.local.entity.MarketDataEntity)
    
    @Query("SELECT * FROM market_data WHERE data_id = :dataId")
    suspend fun getMarketDataById(dataId: String): com.mugiwara.data.local.entity.MarketDataEntity?
    
    @Query("SELECT * FROM market_data WHERE symbol = :symbol")
    fun getSymbolMarketData(symbol: String): Flow<com.mugiwara.data.local.entity.MarketDataEntity?>
    
    @Query("SELECT * FROM market_data")
    fun getAllMarketData(): Flow<List<com.mugiwara.data.local.entity.MarketDataEntity>>
    
    @Query("UPDATE market_data SET market_sentiment = :sentiment WHERE symbol = :symbol")
    suspend fun updateMarketSentiment(symbol: String, sentiment: String)
}
