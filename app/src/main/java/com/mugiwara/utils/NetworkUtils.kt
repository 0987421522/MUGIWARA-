package com.mugiwara.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Utility object for creating and managing Retrofit instances
 */
object RetrofitClient {
    
    companion object {
        private const val TAG = "RetrofitClient"
        private const val CONNECT_TIMEOUT = 30L
        private const val READ_TIMEOUT = 60L
        private const val WRITE_TIMEOUT = 60L
    }
    
    private var retrofit: Retrofit? = null
    
    /**
     * Get or create Retrofit instance
     * @param baseUrl MT5 API base URL
     * @return Retrofit instance
     */
    fun getInstance(baseUrl: String): Retrofit {
        if (retrofit == null || retrofit?.baseUrl()?.toString() != baseUrl) {
            retrofit = createRetrofitInstance(baseUrl)
        }
        return retrofit!!
    }
    
    /**
     * Create new Retrofit instance with OkHttp client
     */
    private fun createRetrofitInstance(baseUrl: String): Retrofit {
        val httpClient = createOkHttpClient()
        
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    /**
     * Create OkHttp client with logging and timeouts
     */
    private fun createOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d(TAG, message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }
}

/**
 * Sealed class for representing API results
 */
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
    
    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }
    
    fun exceptionOrNull(): Exception? = when (this) {
        is Error -> exception
        else -> null
    }
    
    inline fun <R> map(transform: (T) -> R): Result<R> = when (this) {
        is Success -> Success(transform(data))
        is Error -> Error(exception)
        is Loading -> Loading
    }
}
