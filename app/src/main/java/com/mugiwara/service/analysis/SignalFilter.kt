package com.mugiwara.service.analysis

import com.mugiwara.domain.model.Signal
import com.mugiwara.domain.model.Settings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignalFilter @Inject constructor() {

    fun filterSignals(signals: List<Signal>, settings: Settings, dailyProfit: Double): List<Signal> {
        return signals.filter { signal ->
            // Check if max daily loss limit is reached
            if (dailyProfit <= -settings.maxDailyLoss) {
                return@filter false
            }

            // Check if max daily trades limit is reached
            if (settings.maxDailyTrades <= 0) {
                return@filter false
            }

            // Check signal confidence threshold
            if (signal.confidence < 0.5) {
                return@filter false
            }

            true
        }
    }
}
