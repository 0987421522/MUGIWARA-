package com.mugiwara.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.mugiwara.R
import com.mugiwara.presentation.ui.MainActivity
import com.mugiwara.utils.Constants
import javax.inject.Inject
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Singleton
class NotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    
    init {
        createChannels()
    }
    
    private fun createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                NotificationChannel(
                    Constants.NOTIFICATION_CHANNEL_ID,
                    Constants.NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Trading notifications"
                    enableVibration(true)
                },
                NotificationChannel(
                    "market_channel",
                    "Market Updates",
                    NotificationManager.IMPORTANCE_DEFAULT
                ),
                NotificationChannel(
                    "trade_channel",
                    "Trade Updates",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
            
            channels.forEach { notificationManager.createNotificationChannel(it) }
        }
    }
    
    fun showTradeOpenedNotification(symbol: String, type: String, lotSize: Double) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        
        val notification = NotificationCompat.Builder(context, "trade_channel")
            .setContentTitle("Trade Opened")
            .setContentText("$type $symbol @ ${String.format("%.2f", lotSize)} lots")
            .setSmallIcon(android.R.drawable.ic_menu_info_details)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
    
    fun showTradeClosedNotification(symbol: String, profit: Double) {
        val isProfit = profit >= 0
        val color = if (isProfit) android.graphics.Color.GREEN else android.graphics.Color.RED
        
        val notification = NotificationCompat.Builder(context, "trade_channel")
            .setContentTitle("Trade Closed")
            .setContentText("$symbol: ${if (isProfit) "+" else ""}${String.format("%.2f", profit)}")
            .setSmallIcon(android.R.drawable.ic_menu_info_details)
            .setColor(color)
            .setAutoCancel(true)
            .build()
        
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
    
    fun showMarketOpenNotification(market: String) {
        val notification = NotificationCompat.Builder(context, "market_channel")
            .setContentTitle("Market Open")
            .setContentText("$market is now open for trading")
            .setSmallIcon(android.R.drawable.ic_menu_info_details)
            .setAutoCancel(true)
            .build()
        
        notificationManager.notify(market.hashCode(), notification)
    }
    
    fun showMarketClosedNotification(market: String) {
        val notification = NotificationCompat.Builder(context, "market_channel")
            .setContentTitle("Market Closed")
            .setContentText("$market is now closed")
            .setSmallIcon(android.R.drawable.ic_menu_info_details)
            .setAutoCancel(true)
            .build()
        
        notificationManager.notify(market.hashCode(), notification)
    }
    
    fun showProfitNotification(symbol: String, profit: Double) {
        val notification = NotificationCompat.Builder(context, "trade_channel")
            .setContentTitle("Profit Alert")
            .setContentText("$symbol: +${String.format("%.2f", profit)}")
            .setSmallIcon(android.R.drawable.ic_menu_info_details)
            .setColor(android.graphics.Color.GREEN)
            .setAutoCancel(true)
            .build()
        
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
    
    fun showLossNotification(symbol: String, loss: Double) {
        val notification = NotificationCompat.Builder(context, "trade_channel")
            .setContentTitle("Loss Alert")
            .setContentText("$symbol: ${String.format("%.2f", loss)}")
            .setSmallIcon(android.R.drawable.ic_menu_info_details)
            .setColor(android.graphics.Color.RED)
            .setAutoCancel(true)
            .build()
        
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
