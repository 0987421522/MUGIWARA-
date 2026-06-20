package com.mugiwara.data.local

import androidx.room.*
import com.mugiwara.data.model.SettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings WHERE id = 1")
    fun getSettings(): Flow<SettingsEntity?>
    
    @Query("SELECT * FROM settings WHERE id = 1")
    suspend fun getSettingsSync(): SettingsEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: SettingsEntity)
    
    @Update
    suspend fun updateSettings(settings: SettingsEntity)
    
    @Query("UPDATE settings SET autoTrading = :enabled WHERE id = 1")
    suspend fun updateAutoTrading(enabled: Boolean)
    
    @Query("UPDATE settings SET notifications = :enabled WHERE id = 1")
    suspend fun updateNotifications(enabled: Boolean)
    
    @Query("UPDATE settings SET darkMode = :enabled WHERE id = 1")
    suspend fun updateDarkMode(enabled: Boolean)
    
    @Query("UPDATE settings SET riskManagement = :enabled WHERE id = 1")
    suspend fun updateRiskManagement(enabled: Boolean)
    
    @Query("UPDATE settings SET defaultRiskPercent = :percent WHERE id = 1")
    suspend fun updateRiskPercent(percent: Double)
    
    @Query("UPDATE settings SET maxDailyTrades = :count WHERE id = 1")
    suspend fun updateMaxDailyTrades(count: Int)
}
