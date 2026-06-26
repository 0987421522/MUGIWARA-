package com.mugiwara.data.remote.client

import android.util.Log
import com.mugiwara.data.remote.api.Mt5ApiService
import com.mugiwara.data.remote.dto.*
import com.mugiwara.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Main MT5 API Client
 * Wrapper around Mt5ApiService that handles authentication and error management
 */
@Singleton
class Mt5ApiClient @Inject constructor(
    private val apiService: Mt5ApiService,
    private val authManager: Mt5AuthManager
) {
    
    companion object {
        private const val TAG = "Mt5ApiClient"
    }
    
    /**
     * Authenticate with MT5 server
     * @param login Account login
     * @param password Account password
     * @param server Server address (optional)
     * @return Result of authentication attempt
     */
    suspend fun login(
        login: String,
        password: String,
        server: String? = null
    ): Result<Mt5LoginResponse> = withContext(Dispatchers.IO) {
        return@withContext try {
            if (authManager.authenticate(login, password, server)) {
                Log.d(TAG, "Authentication successful for account: $login")
                Result.Success(Mt5LoginResponse(
                    success = true,
                    token = authManager.getToken() ?: "",
                    accountId = authManager.getAccountId() ?: ""
                ))
            } else {
                Log.e(TAG, "Authentication failed for account: $login")
                Result.Error(Exception("Authentication failed"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Login error", e)
            Result.Error(e)
        }
    }
    
    /**
     * Logout from MT5 server
     * @return Result of logout attempt
     */
    suspend fun logout(): Result<Unit> = withContext(Dispatchers.IO) {
        return@withContext try {
            if (authManager.logout()) {
                Log.d(TAG, "Logout successful")
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Logout failed"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Logout error", e)
            Result.Error(e)
        }
    }
    
    /**
     * Get account information
     * @return Account info or error
     */
    suspend fun getAccountInfo(): Result<Mt5AccountResponse> = withContext(Dispatchers.IO) {
        return@withContext safeApiCall {
            val token = getAuthorizationHeader()
            apiService.getAccountInfo(token)
        }
    }
    
    /**
     * Get current quote for a symbol
     * @param symbol Trading symbol (e.g., EURUSD)
     * @return Quote or error
     */
    suspend fun getQuote(symbol: String): Result<Mt5QuoteResponse> = withContext(Dispatchers.IO) {
        return@withContext safeApiCall {
            val token = getAuthorizationHeader()
            apiService.getQuote(token, symbol)
        }
    }
    
    /**
     * Get market history (candles)
     * @param symbol Trading symbol
     * @param timeframe Candle timeframe (M1, M5, H1, D1, etc.)
     * @param limit Number of candles to retrieve
     * @return Market history or error
     */
    suspend fun getMarketHistory(
        symbol: String,
        timeframe: String = "H1",
        limit: Int = 100
    ): Result<Mt5HistoryResponse> = withContext(Dispatchers.IO) {
        return@withContext safeApiCall {
            val token = getAuthorizationHeader()
            apiService.getMarketHistory(token, symbol, timeframe, limit)
        }
    }
    
    /**
     * Open a new trade
     * @param request Trade details
     * @return Trade result with ticket number or error
     */
    suspend fun openTrade(request: Mt5OpenTradeRequest): Result<Mt5TradeResponse> = withContext(Dispatchers.IO) {
        return@withContext safeApiCall {
            val token = getAuthorizationHeader()
            apiService.openTrade(token, request)
        }
    }
    
    /**
     * Close an existing trade
     * @param ticket Trade ticket number
     * @param request Close trade details
     * @return Trade result or error
     */
    suspend fun closeTrade(
        ticket: String,
        request: Mt5CloseTradeRequest
    ): Result<Mt5TradeResponse> = withContext(Dispatchers.IO) {
        return@withContext safeApiCall {
            val token = getAuthorizationHeader()
            apiService.closeTrade(token, ticket, request)
        }
    }
    
    /**
     * Modify a trade (change SL/TP)
     * @param ticket Trade ticket number
     * @param request Modification details
     * @return Trade result or error
     */
    suspend fun modifyTrade(
        ticket: String,
        request: Mt5ModifyTradeRequest
    ): Result<Mt5TradeResponse> = withContext(Dispatchers.IO) {
        return@withContext safeApiCall {
            val token = getAuthorizationHeader()
            apiService.modifyTrade(token, ticket, request)
        }
    }
    
    /**
     * Get all open positions
     * @return Positions or error
     */
    suspend fun getOpenPositions(): Result<Mt5PositionsResponse> = withContext(Dispatchers.IO) {
        return@withContext safeApiCall {
            val token = getAuthorizationHeader()
            apiService.getOpenPositions(token)
        }
    }
    
    /**
     * Get trade history
     * @param limit Number of trades to retrieve
     * @return Trade history or error
     */
    suspend fun getTradeHistory(limit: Int = 100): Result<Mt5HistoryTradesResponse> = withContext(Dispatchers.IO) {
        return@withContext safeApiCall {
            val token = getAuthorizationHeader()
            apiService.getTradeHistory(token, limit)
        }
    }
    
    /**
     * Get server time
     * @return Server time or error
     */
    suspend fun getServerTime(): Result<Mt5ServerTimeResponse> = withContext(Dispatchers.IO) {
        return@withContext safeApiCall {
            val token = getAuthorizationHeader()
            apiService.getServerTime(token)
        }
    }
    
    /**
     * Check if client is authenticated
     * @return True if valid authentication exists
     */
    fun isAuthenticated(): Boolean {
        return authManager.isAuthenticated()
    }
    
    /**
     * Get current authentication token
     * @return Token or null
     */
    fun getToken(): String? {
        return authManager.getToken()
    }
    
    /**
     * Get authorization header value
     * @return Authorization header or empty string
     */
    private fun getAuthorizationHeader(): String {
        return authManager.getBearerToken() ?: throw Exception("Not authenticated")
    }
    
    /**
     * Safe API call wrapper with error handling
     */
    private suspend inline fun <T> safeApiCall(
        crossinline call: suspend () -> Response<T>
    ): Result<T> {
        return try {
            if (!authManager.isAuthenticated()) {
                return Result.Error(Exception("Not authenticated"))
            }
            
            val response = call()
            
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Log.d(TAG, "API call successful")
                    Result.Success(body)
                } else {
                    Log.e(TAG, "Response body is null")
                    Result.Error(Exception("Empty response body"))
                }
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                Log.e(TAG, "API error: ${response.code()} - $errorMessage")
                Result.Error(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "API call exception", e)
            Result.Error(e)
        }
    }
}
