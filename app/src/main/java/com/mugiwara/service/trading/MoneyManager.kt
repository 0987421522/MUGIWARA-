package com.mugiwara.service.trading

import kotlin.math.pow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoneyManager @Inject constructor() {
    
    private val maxDrawdownPercent = 20.0
    private val targetProfitPercent = 5.0
    private val compoundEnabled = true
    
    fun calculateKellyCriterion(
        winRate: Double,
        averageWin: Double,
        averageLoss: Double
    ): Double {
        if (averageLoss == 0.0) return 0.0
        
        val b = averageWin / averageLoss
        val q = 1.0 - winRate
        
        val kelly = (winRate * b - q) / b
        
        return kelly.coerceIn(0.0, 0.25)
    }
    
    fun calculateMartingaleMultiplier(
        consecutiveLosses: Int,
        baseMultiplier: Double = 2.0
    ): Double {
        return baseMultiplier.pow(consecutiveLosses.toDouble())
    }
    
    fun calculateAntiMartingaleMultiplier(
        consecutiveWins: Int,
        baseMultiplier: Double = 1.5
    ): Double {
        return baseMultiplier.pow(consecutiveWins.toDouble())
    }
    
    fun shouldCompound(
        currentBalance: Double,
        initialBalance: Double,
        profitPercent: Double
    ): Boolean {
        return compoundEnabled && profitPercent >= targetProfitPercent
    }
    
    fun calculateCompoundLotSize(
        baseLotSize: Double,
        currentBalance: Double,
        initialBalance: Double
    ): Double {
        if (!compoundEnabled) return baseLotSize
        
        val growthFactor = currentBalance / initialBalance
        return baseLotSize * kotlin.math.sqrt(growthFactor)
    }
    
    fun checkDrawdown(
        currentBalance: Double,
        peakBalance: Double
    ): DrawdownStatus {
        val drawdown = ((peakBalance - currentBalance) / peakBalance) * 100
        
        return when {
            drawdown >= maxDrawdownPercent -> DrawdownStatus.CRITICAL
            drawdown >= maxDrawdownPercent * 0.7 -> DrawdownStatus.WARNING
            drawdown >= maxDrawdownPercent * 0.5 -> DrawdownStatus.CAUTION
            else -> DrawdownStatus.NORMAL
        }
    }
    
    enum class DrawdownStatus {
        NORMAL, CAUTION, WARNING, CRITICAL
    }
}
