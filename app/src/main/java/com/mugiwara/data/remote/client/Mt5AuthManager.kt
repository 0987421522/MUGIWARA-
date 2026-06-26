package com.mugiwara.data.remote.client

import android.content.SharedPreferences
import com.mugiwara.data.remote.api.Mt5ApiService
import com.mugiwara.data.remote.dto.Mt5LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages MT5 authentication and token lifecycle
 * Handles login, token refresh, and logout operations
 */
@Singleton
class Mt5AuthManager @Inject constructor(
    private val apiService: Mt5ApiService,
    private val preferences: SharedPreferences
) {
    
    companion object {
        private const val PREF_TOKEN = "mt5_token"
        private const val PREF_ACCOUNT_ID = "mt5_account_id"
        private const val PREF_LOGIN_TIME = "mt5_login_time"
        private const val PREF_EXPIRES_IN = "mt5_expires_in"
        private const val PREF_USER_LOGIN = "mt5_user_login"
        private const val TOKEN_BUFFER_TIME = 5 * 60 * 1000L // 5 minutes before expiry
    }
    
    private var currentToken: String? = null
    private var currentAccountId: String? = null
    private var tokenExpireTime: Long = 0L
    
    init {
        // Load stored token on initialization
        loadStoredToken()
    }
    
    /**
     * Authenticate with MT5 server
     * @param login MT5 account login
     * @param password MT5 account password
     * @param server MT5 server address (optional)
     * @return True if authentication successful, false otherwise
     */
    suspend fun authenticate(
        login: String,
        password: String,
        server: String? = null
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val request = Mt5LoginRequest(
                login = login,
                password = password,
                server = server,
                deviceId = getDeviceId()
            )
            
            val response = apiService.login(request)
            
            if (response.isSuccessful && response.body()?.success == true) {
                val body = response.body()!!
                currentToken = body.token
                currentAccountId = body.accountId
                
                // Calculate token expiry time
                val expiresInMs = (body.expiresIn ?: 3600L) * 1000L
                tokenExpireTime = System.currentTimeMillis() + expiresInMs
                
                // Save to SharedPreferences for persistence
                saveToken(
                    token = body.token,
                    accountId = body.accountId,
                    login = login,
                    expiresIn = body.expiresIn ?: 3600L
                )
                
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    /**
     * Get current authentication token
     * Checks if token is expired and refreshes if needed
     * @return Valid authentication token or null
     */
    fun getToken(): String? {
        return if (isTokenValid()) {
            currentToken
        } else {
            null
        }
    }
    
    /**
     * Get Bearer token format for Authorization header
     * @return Authorization header value or null
     */
    fun getBearerToken(): String? {
        val token = getToken() ?: return null
        return "Bearer $token"
    }
    
    /**
     * Get current account ID
     * @return Account ID or null
     */
    fun getAccountId(): String? {
        return currentAccountId
    }
    
    /**
     * Check if current token is valid
     * @return True if token exists and not expired
     */
    fun isTokenValid(): Boolean {
        if (currentToken == null) return false
        
        // Check if token is expired (with buffer time)
        val currentTime = System.currentTimeMillis()
        return currentTime < (tokenExpireTime - TOKEN_BUFFER_TIME)
    }
    
    /**
     * Check if user is authenticated
     * @return True if valid token exists
     */
    fun isAuthenticated(): Boolean {
        return getToken() != null
    }
    
    /**
     * Logout from MT5 server
     * @return True if logout successful
     */
    suspend fun logout(): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            val token = currentToken ?: return@withContext false
            
            val response = apiService.logout("Bearer $token")
            
            if (response.isSuccessful) {
                clearToken()
                true
            } else {
                // Even if API call fails, clear local token
                clearToken()
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            clearToken()
            false
        }
    }
    
    /**
     * Save token to persistent storage
     */
    private fun saveToken(
        token: String,
        accountId: String,
        login: String,
        expiresIn: Long
    ) {
        preferences.edit().apply {
            putString(PREF_TOKEN, token)
            putString(PREF_ACCOUNT_ID, accountId)
            putString(PREF_USER_LOGIN, login)
            putLong(PREF_LOGIN_TIME, System.currentTimeMillis())
            putLong(PREF_EXPIRES_IN, expiresIn)
            apply()
        }
    }
    
    /**
     * Load previously saved token from SharedPreferences
     */
    private fun loadStoredToken() {
        val token = preferences.getString(PREF_TOKEN, null)
        val accountId = preferences.getString(PREF_ACCOUNT_ID, null)
        val loginTime = preferences.getLong(PREF_LOGIN_TIME, 0L)
        val expiresIn = preferences.getLong(PREF_EXPIRES_IN, 3600L)
        
        if (token != null && accountId != null) {
            currentToken = token
            currentAccountId = accountId
            tokenExpireTime = loginTime + (expiresIn * 1000L)
        }
    }
    
    /**
     * Clear stored token
     */
    private fun clearToken() {
        currentToken = null
        currentAccountId = null
        tokenExpireTime = 0L
        
        preferences.edit().apply {
            remove(PREF_TOKEN)
            remove(PREF_ACCOUNT_ID)
            remove(PREF_LOGIN_TIME)
            remove(PREF_EXPIRES_IN)
            remove(PREF_USER_LOGIN)
            apply()
        }
    }
    
    /**
     * Get unique device identifier
     */
    private fun getDeviceId(): String {
        val deviceId = preferences.getString("device_id", null)
        return if (deviceId != null) {
            deviceId
        } else {
            val newId = UUID.randomUUID().toString()
            preferences.edit().putString("device_id", newId).apply()
            newId
        }
    }
}
