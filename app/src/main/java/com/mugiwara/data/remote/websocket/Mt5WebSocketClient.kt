package com.mugiwara.data.remote.websocket

import android.util.Log
import com.google.gson.Gson
import com.mugiwara.data.remote.dto.Mt5QuoteResponse
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * WebSocket listener for MT5 market data streaming
 */
private class Mt5WebSocketListener(
    private val onQuote: (Mt5QuoteResponse) -> Unit,
    private val onError: (Throwable) -> Unit,
    private val onClosed: () -> Unit
) : WebSocketListener() {
    
    companion object {
        private const val TAG = "Mt5WebSocket"
    }
    
    private val gson = Gson()
    
    override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
        Log.d(TAG, "WebSocket connected")
    }
    
    override fun onMessage(webSocket: WebSocket, text: String) {
        try {
            val quote = gson.fromJson(text, Mt5QuoteResponse::class.java)
            onQuote(quote)
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing WebSocket message", e)
            onError(e)
        }
    }
    
    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        try {
            val text = bytes.utf8()
            onMessage(webSocket, text)
        } catch (e: Exception) {
            Log.e(TAG, "Error processing binary WebSocket message", e)
            onError(e)
        }
    }
    
    override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
        Log.e(TAG, "WebSocket failure", t)
        onError(t)
    }
    
    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        Log.d(TAG, "WebSocket closed: $code - $reason")
        onClosed()
    }
}

/**
 * Manages WebSocket connection to MT5 for real-time market data
 */
@Singleton
class Mt5WebSocketClient @Inject constructor(
    private val okHttpClient: OkHttpClient
) {
    
    companion object {
        private const val TAG = "Mt5WebSocketClient"
        private const val RECONNECT_DELAY_MS = 2000L
        private const val MAX_RECONNECT_ATTEMPTS = 5
    }
    
    private var webSocket: WebSocket? = null
    private var isConnected = false
    private var reconnectAttempts = 0
    private val gson = Gson()
    private val scope = CoroutineScope(Dispatchers.IO + Job())
    
    private var onQuoteCallback: ((Mt5QuoteResponse) -> Unit)? = null
    private var onErrorCallback: ((Throwable) -> Unit)? = null
    private var onClosedCallback: (() -> Unit)? = null
    
    /**
     * Connect to MT5 WebSocket
     * @param url WebSocket URL
     * @param token Authentication token
     * @param onQuote Callback when quote received
     * @param onError Callback on error
     * @param onClosed Callback on connection closed
     */
    fun connect(
        url: String,
        token: String,
        onQuote: (Mt5QuoteResponse) -> Unit,
        onError: (Throwable) -> Unit,
        onClosed: () -> Unit
    ) {
        if (isConnected) {
            Log.d(TAG, "Already connected")
            return
        }
        
        this.onQuoteCallback = onQuote
        this.onErrorCallback = onError
        this.onClosedCallback = onClosed
        
        scope.launch {
            try {
                val finalUrl = if (url.contains("?")) {
                    "$url&token=$token"
                } else {
                    "$url?token=$token"
                }
                
                val request = Request.Builder()
                    .url(finalUrl)
                    .header("Authorization", "Bearer $token")
                    .build()
                
                val listener = Mt5WebSocketListener(
                    onQuote = onQuote,
                    onError = { error ->
                        onError(error)
                        scheduleReconnect(url, token)
                    },
                    onClosed = {
                        isConnected = false
                        onClosed()
                        scheduleReconnect(url, token)
                    }
                )
                
                webSocket = okHttpClient.newWebSocket(request, listener)
                isConnected = true
                reconnectAttempts = 0
                Log.d(TAG, "WebSocket connection initiated")
            } catch (e: Exception) {
                Log.e(TAG, "WebSocket connection error", e)
                onError(e)
                scheduleReconnect(url, token)
            }
        }
    }
    
    /**
     * Subscribe to symbol quotes
     * @param symbol Trading symbol to subscribe
     */
    fun subscribe(symbol: String) {
        if (!isConnected) {
            Log.w(TAG, "WebSocket not connected")
            return
        }
        
        val message = """{"action":"subscribe","symbol":"$symbol"}"""
        sendMessage(message)
    }
    
    /**
     * Unsubscribe from symbol quotes
     * @param symbol Trading symbol to unsubscribe
     */
    fun unsubscribe(symbol: String) {
        if (!isConnected) return
        
        val message = """{"action":"unsubscribe","symbol":"$symbol"}"""
        sendMessage(message)
    }
    
    /**
     * Send custom message to WebSocket
     * @param message JSON message to send
     */
    fun sendMessage(message: String) {
        try {
            webSocket?.send(message)
            Log.d(TAG, "Message sent: $message")
        } catch (e: Exception) {
            Log.e(TAG, "Error sending message", e)
            onErrorCallback?.invoke(e)
        }
    }
    
    /**
     * Disconnect WebSocket
     */
    fun disconnect() {
        try {
            webSocket?.close(1000, "Client disconnect")
            isConnected = false
            scope.cancel()
            Log.d(TAG, "WebSocket disconnected")
        } catch (e: Exception) {
            Log.e(TAG, "Error disconnecting", e)
        }
    }
    
    /**
     * Check if WebSocket is connected
     */
    fun isConnected(): Boolean = isConnected
    
    /**
     * Schedule reconnection attempt
     */
    private fun scheduleReconnect(url: String, token: String) {
        if (reconnectAttempts < MAX_RECONNECT_ATTEMPTS) {
            reconnectAttempts++
            scope.launch {
                delay(RECONNECT_DELAY_MS)
                Log.d(TAG, "Attempting reconnect (attempt $reconnectAttempts)")
                connect(
                    url,
                    token,
                    onQuoteCallback ?: { },
                    onErrorCallback ?: { },
                    onClosedCallback ?: { }
                )
            }
        } else {
            Log.e(TAG, "Max reconnection attempts reached")
            onErrorCallback?.invoke(Exception("Max reconnect attempts reached"))
        }
    }
}
