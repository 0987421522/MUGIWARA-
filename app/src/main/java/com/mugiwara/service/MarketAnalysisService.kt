package com.mugiwara.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.mugiwara.data.repository.MarketRepository
import com.mugiwara.data.repository.SettingsRepository
import com.mugiwara.data.repository.SignalRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MarketAnalysisService : Service() {

    @Inject lateinit var marketRepository: MarketRepository
    @Inject lateinit var signalRepository: SignalRepository
    @Inject lateinit var settingsRepository: SettingsRepository
    @Inject lateinit var marketHoursManager: MarketHoursManager
    @Inject lateinit var appNotificationManager: AppNotificationManager

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }
}
