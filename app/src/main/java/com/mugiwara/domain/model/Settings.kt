package com.mugiwara.domain.model

data class Settings(
    val autoTrading: Boolean,
    val notifications: Boolean,
    val darkMode: Boolean,
    val riskManagement: Boolean,
    val defaultRiskPercent: Double,
    val defaultStopLossPips: Int,
    val defaultTakeProfitPips: Int,
    val maxDailyTrades: Int,
    val maxDailyLoss: Double,
    val trailingStopEnabled: Boolean,
    val trailingStopPips: Int
)
