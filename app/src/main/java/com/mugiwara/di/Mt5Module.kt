package com.mugiwara.di

import android.content.Context
import android.content.SharedPreferences
import com.mugiwara.data.remote.api.Mt5ApiService
import com.mugiwara.data.remote.client.Mt5ApiClient
import com.mugiwara.data.remote.client.Mt5AuthManager
import com.mugiwara.data.remote.websocket.Mt5WebSocketClient
import com.mugiwara.utils.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Hilt DI module for MT5 API integration
 */
@Module
@InstallIn(SingletonComponent::class)
object Mt5Module {
    
    companion object {
        private const val MT5_BASE_URL = "https://api.mt5.example.com/"
        private const val MT5_WS_URL = "wss://ws.mt5.example.com/"
    }
    
    /**
     * Provide SharedPreferences for token storage
     */
    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(
            "mt5_preferences",
            Context.MODE_PRIVATE
        )
    }
    
    /**
     * Provide OkHttpClient with logging and timeouts
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }
    
    /**
     * Provide Retrofit instance
     */
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MT5_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    /**
     * Provide Mt5ApiService
     */
    @Singleton
    @Provides
    fun provideMt5ApiService(retrofit: Retrofit): Mt5ApiService {
        return retrofit.create(Mt5ApiService::class.java)
    }
    
    /**
     * Provide Mt5AuthManager
     */
    @Singleton
    @Provides
    fun provideMt5AuthManager(
        apiService: Mt5ApiService,
        preferences: SharedPreferences
    ): Mt5AuthManager {
        return Mt5AuthManager(apiService, preferences)
    }
    
    /**
     * Provide Mt5ApiClient
     */
    @Singleton
    @Provides
    fun provideMt5ApiClient(
        apiService: Mt5ApiService,
        authManager: Mt5AuthManager
    ): Mt5ApiClient {
        return Mt5ApiClient(apiService, authManager)
    }
    
    /**
     * Provide Mt5WebSocketClient
     */
    @Singleton
    @Provides
    fun provideMt5WebSocketClient(okHttpClient: OkHttpClient): Mt5WebSocketClient {
        return Mt5WebSocketClient(okHttpClient)
    }
    
    /**
     * Provide MT5 Base URL
     */
    @Singleton
    @Provides
    @Named("mt5BaseUrl")
    fun provideMt5BaseUrl(): String = MT5_BASE_URL
    
    /**
     * Provide MT5 WebSocket URL
     */
    @Singleton
    @Provides
    @Named("mt5WsUrl")
    fun provideMt5WsUrl(): String = MT5_WS_URL
}
