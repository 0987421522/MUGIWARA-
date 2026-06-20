package com.mugiwara.service.analysis

import com.mugiwara.data.model.SignalEntity
import com.mugiwara.data.repository.TradeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignalFilter @Inject constructor(
    private val tradeRepository: TradeRepository
) {
    private val minConfidence = 0.6
    private val minStrength = 0.5
    private val maxDailyLoss = -100.0
    
    suspend fun evaluateSignal(signal: SignalEntity): FilterResult {
        val checks = listOf(
            checkConfidence(signal),
            checkStrength(signal),
            checkRiskReward(signal),
            checkMarketConditions(signal),
            checkDailyLossLimit()
        )
        
        val failedChecks = checks.filter { !it.passed }
        
        return if (failedChecks.isEmpty()) {
            FilterResult.Passed(signal)
        } else {
            FilterResult.Rejected(signal, failedChecks.map { it.reason })
        }
    }
    
    private fun checkConfidence(signal: SignalEntity): FilterCheck {
        return if (signal.confidence >= minConfidence) {
            FilterCheck(true, "Confidence check passed")
        } else {
            FilterCheck(false, "Confidence too low: ${signal.confidence}")
        }
    }
    
    private fun checkStrength(signal: SignalEntity): FilterCheck {
        return if (signal.strength >= minStrength) {
            FilterCheck(true, "Strength check passed")
        } else {
            FilterCheck(false, "Strength too low: ${signal.strength}")
        }
    }
    
    private fun checkRiskReward(signal: SignalEntity): FilterCheck {
        return if (signal.riskRewardRatio >= 1.5) {
            FilterCheck(true, "Risk/Reward ratio acceptable")
        } else {
            FilterCheck(false, "Risk/Reward ratio too low: ${signal.riskRewardRatio}")
        }
    }
    
    private fun checkMarketConditions(signal: SignalEntity): FilterCheck {
        return FilterCheck(true, "Market conditions acceptable")
    }
    
    private suspend fun checkDailyLossLimit(): FilterCheck {
        val dailyProfit = tradeRepository.getDailyProfit(getStartOfDay())
        return if (dailyProfit >= maxDailyLoss) {
            FilterCheck(true, "Daily loss limit not reached")
        } else {
            FilterCheck(false, "Daily loss limit reached: $dailyProfit")
        }
    }
    
    private fun getStartOfDay(): Long {
        val calendar = java.util.Calendar.getInstance()
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
        calendar.set(java.util.Calendar.MINUTE, 0)
        calendar.set(java.util.Calendar.SECOND, 0)
        calendar.set(java.util.Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
    
    data class FilterCheck(val passed: Boolean, val reason: String)
    
    sealed class FilterResult {
        data class Passed(val signal: SignalEntity) : FilterResult()
        data class Rejected(val signal: SignalEntity, val reasons: List<String>) : FilterResult()
    }
}
