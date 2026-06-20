package com.mugiwara.service

import com.mugiwara.data.model.MarketEntity
import com.mugiwara.data.repository.MarketRepository
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketHoursManager @Inject constructor(
    private val marketRepository: MarketRepository
) {
    private val marketSchedules = mapOf(
        "FOREX" to MarketSchedule(
            openDay = Calendar.SUNDAY,
            openHour = 22, // 10 PM Sunday UTC
            closeDay = Calendar.FRIDAY,
            closeHour = 22 // 10 PM Friday UTC
        ),
        "GOLD" to MarketSchedule(
            openDay = Calendar.SUNDAY,
            openHour = 22,
            closeDay = Calendar.FRIDAY,
            closeHour = 22
        ),
        "SILVER" to MarketSchedule(
            openDay = Calendar.SUNDAY,
            openHour = 22,
            closeDay = Calendar.FRIDAY,
            closeHour = 22
        ),
        "CRYPTO" to MarketSchedule(
            openDay = -1, // Always open
            openHour = 0,
            closeDay = -1,
            closeHour = 0
        ),
        "INDICES" to MarketSchedule(
            openDay = Calendar.MONDAY,
            openHour = 8,
            closeDay = Calendar.FRIDAY,
            closeHour = 22
        ),
        "STOCKS" to MarketSchedule(
            openDay = Calendar.MONDAY,
            openHour = 14, // 2 PM UTC (9:30 AM EST)
            closeDay = Calendar.FRIDAY,
            closeHour = 21 // 9 PM UTC (4 PM EST)
        )
    )
    
    fun isMarketOpen(category: String): Boolean {
        val schedule = marketSchedules[category.uppercase()] ?: return false
        
        if (schedule.openDay == -1) return true // 24/7 markets like crypto
        
        val now = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val currentDay = now.get(Calendar.DAY_OF_WEEK)
        val currentHour = now.get(Calendar.HOUR_OF_DAY)
        
        return when (currentDay) {
            schedule.openDay -> currentHour >= schedule.openHour
            schedule.closeDay -> currentHour < schedule.closeHour
            in schedule.openDay..schedule.closeDay -> true
            else -> false
        }
    }
    
    fun getMarketStatus(category: String): MarketStatus {
        return if (isMarketOpen(category)) {
            MarketStatus.OPEN
        } else {
            MarketStatus.CLOSED
        }
    }
    
    fun getNextOpenTime(category: String): Long {
        val schedule = marketSchedules[category.uppercase()] ?: return 0
        
        if (schedule.openDay == -1) return System.currentTimeMillis()
        
        val now = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val currentDay = now.get(Calendar.DAY_OF_WEEK)
        
        val nextOpen = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        
        if (currentDay == schedule.closeDay && now.get(Calendar.HOUR_OF_DAY) >= schedule.closeHour) {
            nextOpen.add(Calendar.DAY_OF_WEEK, schedule.openDay - schedule.closeDay + 7)
        } else if (currentDay < schedule.openDay || (currentDay == schedule.openDay && now.get(Calendar.HOUR_OF_DAY) < schedule.openHour)) {
            nextOpen.set(Calendar.DAY_OF_WEEK, schedule.openDay)
        } else {
            nextOpen.add(Calendar.DAY_OF_WEEK, 7)
            nextOpen.set(Calendar.DAY_OF_WEEK, schedule.openDay)
        }
        
        nextOpen.set(Calendar.HOUR_OF_DAY, schedule.openHour)
        nextOpen.set(Calendar.MINUTE, 0)
        nextOpen.set(Calendar.SECOND, 0)
        
        return nextOpen.timeInMillis
    }
    
    suspend fun updateMarketStatuses() {
        val markets = marketRepository.getAllMarkets()
        // This would be collected as Flow, but for simplicity:
        // In real implementation, this would iterate through markets and update status
    }
    
    data class MarketSchedule(
        val openDay: Int,
        val openHour: Int,
        val closeDay: Int,
        val closeHour: Int
    )
    
    enum class MarketStatus {
        OPEN, CLOSED, UNKNOWN
    }
}
