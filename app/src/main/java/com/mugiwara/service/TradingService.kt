package com.mugiwara.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.mugiwara.data.repository.AccountRepository
import com.mugiwara.data.repository.SettingsRepository
import com.mugiwara.data.repository.TradeRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TradingService : Service() {

    @Inject lateinit var accountRepository: AccountRepository
    @Inject lateinit var tradeRepository: TradeRepository
    @Inject lateinit var settingsRepository: SettingsRepository
    @Inject lateinit var appNotificationManager: AppNotificationManager

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }
}
