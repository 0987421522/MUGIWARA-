package com.mugiwara.data.local

import androidx.room.*
import com.mugiwara.data.model.MarketEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarketDao {
    @Query("SELECT * FROM markets ORDER BY name ASC")
    fun getAllMarkets(): Flow<List<MarketEntity>>
    
    @Query("SELECT * FROM markets WHERE category = :category ORDER BY name ASC")
    fun getMarketsByCategory(category: String): Flow<List<MarketEntity>>
    
    @Query("SELECT * FROM markets WHERE isOpen = 1 ORDER BY name ASC")
    fun getOpenMarkets(): Flow<List<MarketEntity>>
    
    @Query("SELECT * FROM markets WHERE id = :id")
    suspend fun getMarketById(id: String): MarketEntity?
    
    @Query("SELECT * FROM markets WHERE symbol = :symbol LIMIT 1")
    suspend fun getMarketBySymbol(symbol: String): MarketEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarket(market: MarketEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarkets(markets: List<MarketEntity>)
    
    @Update
    suspend fun updateMarket(market: MarketEntity)
    
    @Delete
    suspend fun deleteMarket(market: MarketEntity)
    
    @Query("DELETE FROM markets WHERE id = :id")
    suspend fun deleteMarketById(id: String)
    
    @Query("UPDATE markets SET isOpen = :isOpen WHERE id = :id")
    suspend fun updateMarketStatus(id: String, isOpen: Boolean)
    
    @Query("SELECT COUNT(*) FROM markets")
    suspend fun getMarketCount(): Int
}
