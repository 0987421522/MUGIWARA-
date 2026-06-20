package com.mugiwara.service.analysis

import com.mugiwara.data.model.MarketEntity
import com.mugiwara.data.model.SignalEntity
import com.mugiwara.data.repository.MarketRepository
import com.mugiwara.data.repository.SignalRepository
import com.mugiwara.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketAnalysisEngine @Inject constructor(
    private val marketRepository: MarketRepository,
    private val signalRepository: SignalRepository
) {
    private val indicators = listOf(
        RSIIndicator(14),
        MACDIndicator(12, 26, 9),
        MovingAverageIndicator(20),
        MovingAverageIndicator(50),
        BollingerBandsIndicator(20, 2.0)
    )
    
    fun analyzeMarket(symbol: String, prices: List<Double>): Flow<SignalEntity> = flow {
        if (prices.size < 50) {
            return@flow
        }
        
        val signals = mutableListOf<SignalType>()
        val indicatorValues = mutableMapOf<String, Double>()
        
        for (indicator in indicators) {
            val value = indicator.calculate(prices)
            val signal = indicator.getSignal(prices)
            signals.add(signal)
            indicatorValues[indicator.getName()] = value
        }
        
        val buyCount = signals.count { it == SignalType.BUY }
        val sellCount = signals.count { it == SignalType.SELL }
        val neutralCount = signals.count { it == SignalType.NEUTRAL }
        
        val confidence = kotlin.math.max(buyCount, sellCount).toDouble() / indicators.size
        val strength = calculateStrength(prices, indicatorValues)
        
        if (confidence >= 0.6 && strength >= 0.5) {
            val direction = if (buyCount > sellCount) "BUY" else "SELL"
            val currentPrice = prices.last()
            val atr = calculateATR(prices, 14)
            
            val signal = SignalEntity(
                id = UUID.randomUUID().toString(),
                marketId = symbol,
                symbol = symbol,
                type = direction,
                direction = direction,
                strength = strength,
                confidence = confidence,
                entryPrice = currentPrice,
                stopLoss = if (direction == "BUY") currentPrice - (atr * 1.5) else currentPrice + (atr * 1.5),
                takeProfit = if (direction == "BUY") currentPrice + (atr * 3.0) else currentPrice - (atr * 3.0),
                riskRewardRatio = 2.0,
                indicators = indicatorValues.toString(),
                timeFrame = "H1",
                isExecuted = false,
                isFiltered = false,
                reason = null,
                createdAt = System.currentTimeMillis(),
                expiresAt = System.currentTimeMillis() + 3600000
            )
            
            emit(signal)
        }
    }
    
    fun analyzeAllMarkets(pricesMap: Map<String, List<Double>>): Flow<List<SignalEntity>> = flow {
        val allSignals = mutableListOf<SignalEntity>()
        
        for ((symbol, prices) in pricesMap) {
            if (prices.size >= 50) {
                val signals = mutableListOf<SignalType>()
                val indicatorValues = mutableMapOf<String, Double>()
                
                for (indicator in indicators) {
                    val value = indicator.calculate(prices)
                    val signal = indicator.getSignal(prices)
                    signals.add(signal)
                    indicatorValues[indicator.getName()] = value
                }
                
                val buyCount = signals.count { it == SignalType.BUY }
                val sellCount = signals.count { it == SignalType.SELL }
                val confidence = kotlin.math.max(buyCount, sellCount).toDouble() / indicators.size
                val strength = calculateStrength(prices, indicatorValues)
                
                if (confidence >= 0.6 && strength >= 0.5) {
                    val direction = if (buyCount > sellCount) "BUY" else "SELL"
                    val currentPrice = prices.last()
                    val atr = calculateATR(prices, 14)
                    
                    val signal = SignalEntity(
                        id = UUID.randomUUID().toString(),
                        marketId = symbol,
                        symbol = symbol,
                        type = direction,
                        direction = direction,
                        strength = strength,
                        confidence = confidence,
                        entryPrice = currentPrice,
                        stopLoss = if (direction == "BUY") currentPrice - (atr * 1.5) else currentPrice + (atr * 1.5),
                        takeProfit = if (direction == "BUY") currentPrice + (atr * 3.0) else currentPrice - (atr * 3.0),
                        riskRewardRatio = 2.0,
                        indicators = indicatorValues.toString(),
                        timeFrame = "H1",
                        isExecuted = false,
                        isFiltered = false,
                        reason = null,
                        createdAt = System.currentTimeMillis(),
                        expiresAt = System.currentTimeMillis() + 3600000
                    )
                    
                    allSignals.add(signal)
                }
            }
        }
        
        emit(allSignals)
    }
    
    private fun calculateStrength(prices: List<Double>, indicatorValues: Map<String, Double>): Double {
        if (prices.size < 2) return 0.0
        
        val trend = calculateTrend(prices)
        val volatility = calculateVolatility(prices)
        val momentum = indicatorValues["RSI(14)"] ?: 50.0
        
        val trendStrength = kotlin.math.abs(trend).coerceIn(0.0, 1.0)
        val momentumStrength = kotlin.math.abs(momentum - 50.0) / 50.0
        val volatilityFactor = if (volatility > 0.001) 1.0 else 0.5
        
        return (trendStrength * 0.4 + momentumStrength * 0.4 + volatilityFactor * 0.2)
    }
    
    private fun calculateTrend(prices: List<Double>): Double {
        if (prices.size < 10) return 0.0
        val recent = prices.takeLast(10).average()
        val older = prices.dropLast(10).takeLast(10).average()
        return (recent - older) / older
    }
    
    private fun calculateVolatility(prices: List<Double>): Double {
        if (prices.size < 2) return 0.0
        val returns = mutableListOf<Double>()
        for (i in 1 until prices.size) {
            returns.add(kotlin.math.abs(prices[i] - prices[i - 1]) / prices[i - 1])
        }
        return returns.average()
    }
    
    private fun calculateATR(prices: List<Double>, period: Int): Double {
        if (prices.size < period + 1) return 0.0
        val ranges = mutableListOf<Double>()
        for (i in prices.size - period until prices.size) {
            ranges.add(kotlin.math.abs(prices[i] - prices[i - 1]))
        }
        return ranges.average()
    }
}
