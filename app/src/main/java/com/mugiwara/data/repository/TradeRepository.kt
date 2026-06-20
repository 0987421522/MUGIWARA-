package com.mugiwara.data.repository

import com.mugiwara.data.local.TradeDao
import com.mugiwara.data.model.TradeEntity
import com.mugiwara.data.remote.MT5ApiService
import com.mugiwara.data.remote.dto.CloseTradeRequest
import com.mugiwara.data.remote.dto.ModifyTradeRequest
import com.mugiwara.data.remote.dto.OpenTradeRequest
import com.mugiwara.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TradeRepository @Inject constructor(
    private val tradeDao: TradeDao,
    private val apiService: MT5ApiService
) {
    fun getAllTrades(): Flow<List<TradeEntity>> = tradeDao.getAllTrades()
    
    fun getActiveTrades(): Flow<List<TradeEntity>> = tradeDao.getActiveTrades()
    
    fun getClosedTrades(): Flow<List<TradeEntity>> = tradeDao.getClosedTrades()
    
    fun getTradesByAccount(accountId: String): Flow<List<TradeEntity>> = 
        tradeDao.getTradesByAccount(accountId)
    
    suspend fun getTradeById(id: String): TradeEntity? = tradeDao.getTradeById(id)
    
    suspend fun addTrade(trade: TradeEntity) = tradeDao.insertTrade(trade)
    
    suspend fun updateTrade(trade: TradeEntity) = tradeDao.updateTrade(trade)
    
    suspend fun deleteTrade(id: String) = tradeDao.deleteTradeById(id)
    
    suspend fun getActiveTradeCount(): Int = tradeDao.getActiveTradeCount()
    
    suspend fun getDailyProfit(startOfDay: Long): Double = 
        tradeDao.getDailyProfit(startOfDay) ?: 0.0
    
    suspend fun getTotalProfit(): Double = tradeDao.getTotalProfit() ?: 0.0
    
    suspend fun getWinRate(): Double {
        val total = tradeDao.getTotalClosedCount()
        if (total == 0) return 0.0
        val wins = tradeDao.getWinCount()
        return (wins.toDouble() / total) * 100
    }
    
    suspend fun openTrade(token: String, request: OpenTradeRequest): Result<Unit> {
        return try {
            val response = apiService.openTrade(token, request)
            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Failed to open trade: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    suspend fun closeTrade(token: String, request: CloseTradeRequest): Result<Unit> {
        return try {
            val response = apiService.closeTrade(token, request)
            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Failed to close trade: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    suspend fun modifyTrade(token: String, request: ModifyTradeRequest): Result<Unit> {
        return try {
            val response = apiService.modifyTrade(token, request)
            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Failed to modify trade: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
