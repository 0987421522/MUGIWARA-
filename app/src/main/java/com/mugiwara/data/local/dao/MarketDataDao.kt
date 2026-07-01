package com.mugiwara.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mugiwara.data.local.entity.MarketDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarketDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarketData(marketData: MarketDataEntity)

    // استخدام data_id بدلاً من id
    @Query("SELECT * FROM market_data WHERE data_id = :id")
    suspend fun getMarketDataById(id: Long): MarketDataEntity?

    @Query("SELECT * FROM market_data WHERE symbol = :symbol")
    fun getMarketDataBySymbol(symbol: String): Flow<MarketDataEntity?>

    // استخدام market_sentiment (العمود الجديد)
    @Query("UPDATE market_data SET market_sentiment = :sentiment WHERE symbol = :symbol")
    suspend fun updateMarketSentiment(symbol: String, sentiment: String)

    @Query("DELETE FROM market_data")
    suspend fun deleteAll()
}
