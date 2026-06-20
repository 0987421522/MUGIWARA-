package com.mugiwara.data.repository

import com.mugiwara.data.local.MarketDao
import com.mugiwara.data.model.MarketEntity
import com.mugiwara.data.remote.MT5ApiService
import com.mugiwara.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketRepository @Inject constructor(
    private val marketDao: MarketDao,
    private val apiService: MT5ApiService
) {
    fun getAllMarkets(): Flow<List<MarketEntity>> = marketDao.getAllMarkets()
    
    fun getMarketsByCategory(category: String): Flow<List<MarketEntity>> = 
        marketDao.getMarketsByCategory(category)
    
    fun getOpenMarkets(): Flow<List<MarketEntity>> = marketDao.getOpenMarkets()
    
    suspend fun getMarketById(id: String): MarketEntity? = marketDao.getMarketById(id)
    
    suspend fun getMarketBySymbol(symbol: String): MarketEntity? = marketDao.getMarketBySymbol(symbol)
    
    suspend fun addMarket(market: MarketEntity) = marketDao.insertMarket(market)
    
    suspend fun addMarkets(markets: List<MarketEntity>) = marketDao.insertMarkets(markets)
    
    suspend fun updateMarket(market: MarketEntity) = marketDao.updateMarket(market)
    
    suspend fun deleteMarket(id: String) = marketDao.deleteMarketById(id)
    
    suspend fun updateMarketStatus(id: String, isOpen: Boolean) = 
        marketDao.updateMarketStatus(id, isOpen)
    
    suspend fun getMarketCount(): Int = marketDao.getMarketCount()
    
    suspend fun fetchMarketPrices(token: String): Result<List<MarketEntity>> {
        return try {
            val response = apiService.getMarketPrices(token)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val markets = body.map { dto ->
                        MarketEntity(
                            id = dto.symbol,
                            name = dto.symbol,
                            symbol = dto.symbol,
                            category = "Forex",
                            price = (dto.bid + dto.ask) / 2,
                            bid = dto.bid,
                            ask = dto.ask,
                            spread = dto.spread,
                            change = dto.change,
                            changePercent = dto.changePercent,
                            high24h = dto.high,
                            low24h = dto.low,
                            volume24h = dto.volume,
                            isOpen = true,
                            openTime = 0,
                            closeTime = 0,
                            timezone = "UTC",
                            updatedAt = System.currentTimeMillis()
                        )
                    }
                    marketDao.insertMarkets(markets)
                    Result.Success(markets)
                } else {
                    Result.Error(Exception("Empty response body"))
                }
            } else {
                Result.Error(Exception("Failed to fetch prices: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
