package com.mugiwara.data.remote.api

import com.mugiwara.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Retrofit interface for MetaTrader 5 API communication
 * Handles all REST API calls to MT5 server
 */
interface Mt5ApiService {
    
    /**
     * Authenticate with MT5 server using credentials
     * @param request Login credentials
     * @return Authentication response with token
     */
    @POST("auth/login")
    suspend fun login(@Body request: Mt5LoginRequest): Response<Mt5LoginResponse>
    
    /**
     * Logout from MT5 server
     * @param token Authentication token
     * @return Logout confirmation
     */
    @POST("auth/logout")
    suspend fun logout(
        @Header("Authorization") token: String
    ): Response<Mt5LogoutResponse>
    
    /**
     * Get current account information
     * @param token Authentication token
     * @return Account details
     */
    @GET("account/info")
    suspend fun getAccountInfo(
        @Header("Authorization") token: String
    ): Response<Mt5AccountResponse>
    
    /**
     * Get current symbol quote/price
     * @param token Authentication token
     * @param symbol Trading symbol (e.g., EURUSD)
     * @return Current quote information
     */
    @GET("market/quote/{symbol}")
    suspend fun getQuote(
        @Header("Authorization") token: String,
        @Path("symbol") symbol: String
    ): Response<Mt5QuoteResponse>
    
    /**
     * Get market data history for a symbol
     * @param token Authentication token
     * @param symbol Trading symbol
     * @param timeframe Candle timeframe (M1, M5, H1, D1, etc.)
     * @param limit Number of candles to retrieve
     * @return List of OHLC candles
     */
    @GET("market/history/{symbol}")
    suspend fun getMarketHistory(
        @Header("Authorization") token: String,
        @Path("symbol") symbol: String,
        @Query("timeframe") timeframe: String = "H1",
        @Query("limit") limit: Int = 100
    ): Response<Mt5HistoryResponse>
    
    /**
     * Open a new trade position
     * @param token Authentication token
     * @param request Trade details (symbol, volume, price, SL, TP, etc.)
     * @return Trade execution result with ticket number
     */
    @POST("trade/open")
    suspend fun openTrade(
        @Header("Authorization") token: String,
        @Body request: Mt5OpenTradeRequest
    ): Response<Mt5TradeResponse>
    
    /**
     * Close an existing trade position
     * @param token Authentication token
     * @param ticket Trade ticket number to close
     * @param request Close trade details (volume, price)
     * @return Trade closure result
     */
    @POST("trade/close/{ticket}")
    suspend fun closeTrade(
        @Header("Authorization") token: String,
        @Path("ticket") ticket: String,
        @Body request: Mt5CloseTradeRequest
    ): Response<Mt5TradeResponse>
    
    /**
     * Modify an existing trade (SL/TP)
     * @param token Authentication token
     * @param ticket Trade ticket number to modify
     * @param request New SL/TP values
     * @return Modification result
     */
    @POST("trade/modify/{ticket}")
    suspend fun modifyTrade(
        @Header("Authorization") token: String,
        @Path("ticket") ticket: String,
        @Body request: Mt5ModifyTradeRequest
    ): Response<Mt5TradeResponse>
    
    /**
     * Get all open positions
     * @param token Authentication token
     * @return List of open positions
     */
    @GET("trade/positions")
    suspend fun getOpenPositions(
        @Header("Authorization") token: String
    ): Response<Mt5PositionsResponse>
    
    /**
     * Get trade history
     * @param token Authentication token
     * @param limit Number of trades to retrieve
     * @return List of closed trades
     */
    @GET("trade/history")
    suspend fun getTradeHistory(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 100
    ): Response<Mt5HistoryTradesResponse>
    
    /**
     * Get current server time
     * @param token Authentication token
     * @return Server time information
     */
    @GET("server/time")
    suspend fun getServerTime(
        @Header("Authorization") token: String
    ): Response<Mt5ServerTimeResponse>
}
