package com.mugiwara.data.repository

import com.mugiwara.data.local.SettingsDao
import com.mugiwara.data.model.SettingsEntity
import com.mugiwara.utils.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val settingsDao: SettingsDao
) {
    fun getSettings(): Flow<SettingsEntity?> = settingsDao.getSettings()
    
    suspend fun getSettingsSync(): SettingsEntity? = settingsDao.getSettingsSync()
    
    suspend fun saveSettings(settings: SettingsEntity) = settingsDao.insertSettings(settings)
    
    suspend fun updateAutoTrading(enabled: Boolean) = settingsDao.updateAutoTrading(enabled)
    
    suspend fun updateNotifications(enabled: Boolean) = settingsDao.updateNotifications(enabled)
    
    suspend fun updateDarkMode(enabled: Boolean) = settingsDao.updateDarkMode(enabled)
    
    suspend fun updateRiskManagement(enabled: Boolean) = settingsDao.updateRiskManagement(enabled)
    
    suspend fun updateRiskPercent(percent: Double) = settingsDao.updateRiskPercent(percent)
    
    suspend fun updateMaxDailyTrades(count: Int) = settingsDao.updateMaxDailyTrades(count)
    
    suspend fun getDefaultSettings(): SettingsEntity {
        return SettingsEntity(
            id = 1,
            autoTrading = false,
            notifications = true,
            darkMode = true,
            riskManagement = true,
            defaultRiskPercent = Constants.DEFAULT_RISK_PERCENT,
            defaultStopLossPips = Constants.DEFAULT_STOP_LOSS_PIPS,
            defaultTakeProfitPips = Constants.DEFAULT_TAKE_PROFIT_PIPS,
            maxDailyTrades = 10,
            maxDailyLoss = 100.0,
            trailingStopEnabled = false,
            trailingStopPips = 20,
            updatedAt = System.currentTimeMillis()
        )
    }
    
    suspend fun initializeSettings() {
        val existing = settingsDao.getSettingsSync()
        if (existing == null) {
            settingsDao.insertSettings(getDefaultSettings())
        }
    }
}
