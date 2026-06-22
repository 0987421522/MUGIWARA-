package com.mugiwara.data.remote

import com.mugiwara.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface MT5ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<Unit>

    @GET("account/info")
    suspend fun getAccountInfo(@Header("Authorization") token: String): Response<AccountInfoResponse>

    @GET("market/prices")
    suspend fun getMarketPrices(@Header("Authorization") token: String): Response<List<MarketPriceResponse>>

    @GET("market/symbols")
    suspend fun getSymbols(@Header("Authorization") token: String): Response<List<SymbolResponse>>

    @POST("trade/open")
    suspend fun openTrade(
        @Header("Authorization") token: String,
        @Body request: OpenTradeRequest
    ): Response<TradeResponse>

    @POST("trade/close")
    suspend fun closeTrade(
        @Header("Authorization") token: String,
        @Body request: CloseTradeRequest
    ): Response<Unit>

    @POST("trade/modify")
    suspend fun modifyTrade(
        @Header("Authorization") token: String,
        @Body request: ModifyTradeRequest
    ): Response<Unit>

    @GET("trade/positions")
    suspend fun getPositions(@Header("Authorization") token: String): Response<List<TradeResponse>>

    @GET("trade/history")
    suspend fun getTradeHistory(
        @Header("Authorization") token: String,
        @Query("from") from: Long,
        @Query("to") to: Long
    ): Response<List<TradeResponse>>

    @GET("market/tick/{symbol}")
    suspend fun getTick(
        @Header("Authorization") token: String,
        @Path("symbol") symbol: String
    ): Response<TickResponse>
}
