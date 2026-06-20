package com.mugiwara.service.trading

import com.mugiwara.data.model.AccountEntity
import com.mugiwara.data.model.TradeEntity
import com.mugiwara.domain.model.Settings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RiskManager @Inject constructor() {
    
    fun calculateLotSize(
        accountBalance: Double,
        riskPercent: Double,
        stopLossPips: Double,
        pipValue: Double,
        symbol: String
    ): Double {
        val riskAmount = accountBalance * (riskPercent / 100.0)
        val lotSize = riskAmount / (stopLossPips * pipValue)
        
        return lotSize.coerceIn(
            getMinLot(symbol),
            getMaxLot(symbol)
        )
    }
    
    fun calculatePositionSize(
        accountBalance: Double,
        riskPercent: Double,
        entryPrice: Double,
        stopLoss: Double,
        symbol: String
    ): Double {
        val riskAmount = accountBalance * (riskPercent / 100.0)
        val priceRisk = kotlin.math.abs(entryPrice - stopLoss)
        
        if (priceRisk == 0.0) return getMinLot(symbol)
        
        val lotSize = riskAmount / priceRisk
        return lotSize.coerceIn(
            getMinLot(symbol),
            getMaxLot(symbol)
        )
    }
    
    fun validateTrade(
        account: AccountEntity,
        trade: TradeEntity,
        settings: Settings,
        dailyTradeCount: Int,
        dailyLoss: Double
    ): RiskValidation {
        val checks = mutableListOf<RiskCheck>()
        
        // Check 1: Sufficient balance
        checks.add(
            if (account.freeMargin >= trade.lotSize * 1000) {
                RiskCheck(true, "Sufficient margin")
            } else {
                RiskCheck(false, "Insufficient margin: ${account.freeMargin}")
            }
        )
        
        // Check 2: Daily trade limit
        checks.add(
            if (dailyTradeCount < settings.maxDailyTrades) {
                RiskCheck(true, "Daily trade limit not reached")
            } else {
                RiskCheck(false, "Daily trade limit reached: ${settings.maxDailyTrades}")
            }
        )
        
        // Check 3: Daily loss limit
        checks.add(
            if (dailyLoss > -settings.maxDailyLoss) {
                RiskCheck(true, "Daily loss limit not reached")
            } else {
                RiskCheck(false, "Daily loss limit reached: $dailyLoss")
            }
        )
        
        // Check 4: Risk per trade
        val riskAmount = kotlin.math.abs(trade.entryPrice - trade.stopLoss) * trade.lotSize
        val riskPercent = (riskAmount / account.balance) * 100
        checks.add(
            if (riskPercent <= settings.defaultRiskPercent) {
                RiskCheck(true, "Risk per trade acceptable: ${String.format("%.2f", riskPercent)}%")
            } else {
                RiskCheck(false, "Risk per trade too high: ${String.format("%.2f", riskPercent)}%")
            }
        )
        
        // Check 5: Stop Loss mandatory
        checks.add(
            if (trade.stopLoss > 0) {
                RiskCheck(true, "Stop Loss set")
            } else {
                RiskCheck(false, "Stop Loss not set")
            }
        )
        
        val failedChecks = checks.filter { !it.passed }
        
        return if (failedChecks.isEmpty()) {
            RiskValidation.Passed(checks)
        } else {
            RiskValidation.Failed(checks, failedChecks)
        }
    }
    
    fun calculateTrailingStop(
        currentPrice: Double,
        entryPrice: Double,
        initialStopLoss: Double,
        trailingPips: Double,
        direction: String
    ): Double {
        return if (direction == "BUY") {
            val newStop = currentPrice - trailingPips
            kotlin.math.max(newStop, initialStopLoss)
        } else {
            val newStop = currentPrice + trailingPips
            kotlin.math.min(newStop, initialStopLoss)
        }
    }
    
    private fun getMinLot(symbol: String): Double {
        return when {
            symbol.contains("XAU") || symbol.contains("XAG") -> 0.01
            symbol.contains("BTC") || symbol.contains("ETH") -> 0.01
            else -> 0.01
        }
    }
    
    private fun getMaxLot(symbol: String): Double {
        return when {
            symbol.contains("XAU") || symbol.contains("XAG") -> 100.0
            symbol.contains("BTC") || symbol.contains("ETH") -> 10.0
            else -> 100.0
        }
    }
    
    data class RiskCheck(val passed: Boolean, val message: String)
    
    sealed class RiskValidation {
        data class Passed(val checks: List<RiskCheck>) : RiskValidation()
        data class Failed(val checks: List<RiskCheck>, val failedChecks: List<RiskCheck>) : RiskValidation()
    }
}
