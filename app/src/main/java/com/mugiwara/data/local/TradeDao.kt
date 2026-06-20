package com.mugiwara.data.local

import androidx.room.*
import com.mugiwara.data.model.TradeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TradeDao {
    @Query("SELECT * FROM trades ORDER BY openTime DESC")
    fun getAllTrades(): Flow<List<TradeEntity>>
    
    @Query("SELECT * FROM trades WHERE status = 'ACTIVE' ORDER BY openTime DESC")
    fun getActiveTrades(): Flow<List<TradeEntity>>
    
    @Query("SELECT * FROM trades WHERE status = 'CLOSED' ORDER BY closeTime DESC")
    fun getClosedTrades(): Flow<List<TradeEntity>>
    
    @Query("SELECT * FROM trades WHERE accountId = :accountId ORDER BY openTime DESC")
    fun getTradesByAccount(accountId: String): Flow<List<TradeEntity>>
    
    @Query("SELECT * FROM trades WHERE id = :id")
    suspend fun getTradeById(id: String): TradeEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrade(trade: TradeEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrades(trades: List<TradeEntity>)
    
    @Update
    suspend fun updateTrade(trade: TradeEntity)
    
    @Delete
    suspend fun deleteTrade(trade: TradeEntity)
    
    @Query("DELETE FROM trades WHERE id = :id")
    suspend fun deleteTradeById(id: String)
    
    @Query("SELECT COUNT(*) FROM trades WHERE status = 'ACTIVE'")
    suspend fun getActiveTradeCount(): Int
    
    @Query("SELECT SUM(profit) FROM trades WHERE status = 'CLOSED' AND closeTime >= :startOfDay")
    suspend fun getDailyProfit(startOfDay: Long): Double?
    
    @Query("SELECT SUM(profit) FROM trades WHERE status = 'CLOSED'")
    suspend fun getTotalProfit(): Double?
    
    @Query("SELECT COUNT(*) FROM trades WHERE status = 'CLOSED' AND profit > 0")
    suspend fun getWinCount(): Int
    
    @Query("SELECT COUNT(*) FROM trades WHERE status = 'CLOSED'")
    suspend fun getTotalClosedCount(): Int
}
