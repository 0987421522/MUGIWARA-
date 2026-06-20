package com.mugiwara.data.mapper

import com.mugiwara.data.model.SettingsEntity
import com.mugiwara.domain.model.Settings

object SettingsMapper {
    fun toDomain(entity: SettingsEntity): Settings {
        return Settings(
            autoTrading = entity.autoTrading,
            notifications = entity.notifications,
            darkMode = entity.darkMode,
            riskManagement = entity.riskManagement,
            defaultRiskPercent = entity.defaultRiskPercent,
            defaultStopLossPips = entity.defaultStopLossPips,
            defaultTakeProfitPips = entity.defaultTakeProfitPips,
            maxDailyTrades = entity.maxDailyTrades,
            maxDailyLoss = entity.maxDailyLoss,
            trailingStopEnabled = entity.trailingStopEnabled,
            trailingStopPips = entity.trailingStopPips
        )
    }
    
    fun toEntity(domain: Settings): SettingsEntity {
        return SettingsEntity(
            id = 1,
            autoTrading = domain.autoTrading,
            notifications = domain.notifications,
            darkMode = domain.darkMode,
            riskManagement = domain.riskManagement,
            defaultRiskPercent = domain.defaultRiskPercent,
            defaultStopLossPips = domain.defaultStopLossPips,
            defaultTakeProfitPips = domain.defaultTakeProfitPips,
            maxDailyTrades = domain.maxDailyTrades,
            maxDailyLoss = domain.maxDailyLoss,
            trailingStopEnabled = domain.trailingStopEnabled,
            trailingStopPips = domain.trailingStopPips,
            updatedAt = System.currentTimeMillis()
        )
    }
}
