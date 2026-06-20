package com.mugiwara.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey
    val id: Int = 1,
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
    val trailingStopPips: Int,
    val updatedAt: Long
)
