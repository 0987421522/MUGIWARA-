package com.mugiwara.domain.model

data class Settings(
    val autoTrading: Boolean = false,
    val notifications: Boolean = true,
    val darkMode: Boolean = true,
    val riskManagement: Boolean = true,
    val defaultRiskPercent: Double = 1.0,
    val defaultStopLossPips: Int = 20,
    val defaultTakeProfitPips: Int = 40,
    val maxDailyTrades: Int = 10,
    val maxDailyLoss: Double = 100.0,
    val trailingStopEnabled: Boolean = false,
    val trailingStopPips: Int = 20
)
