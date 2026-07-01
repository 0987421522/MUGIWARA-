package com.mugiwara.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mugiwara.data.local.entity.CandleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CandleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCandle(candle: CandleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(candles: List<CandleEntity>)

    // استخدام candle_id بدلاً من id
    @Query("SELECT * FROM candles WHERE candle_id = :id")
    suspend fun getCandleById(id: Long): CandleEntity?

    // استخدام timestamp بدلاً من openTime
    @Query("SELECT * FROM candles WHERE symbol = :symbol AND timeframe = :timeframe AND timestamp >= :startTime ORDER BY timestamp DESC")
    suspend fun getRecentCandles(symbol: String, timeframe: String, startTime: Long): List<CandleEntity>

    @Query("SELECT * FROM candles WHERE symbol = :symbol AND timeframe = :timeframe AND timestamp BETWEEN :startTime AND :endTime ORDER BY timestamp ASC")
    suspend fun getCandlesByDateRange(symbol: String, timeframe: String, startTime: Long, endTime: Long): List<CandleEntity>

    @Query("DELETE FROM candles WHERE timestamp < :timestamp")
    suspend fun deleteOldCandles(timestamp: Long)
}
