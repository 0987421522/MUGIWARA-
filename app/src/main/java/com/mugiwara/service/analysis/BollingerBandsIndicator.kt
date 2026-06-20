package com.mugiwara.service.analysis

import kotlin.math.sqrt

class BollingerBandsIndicator(
    private val period: Int = 20,
    private val deviations: Double = 2.0
) : TechnicalIndicator {
    
    data class BollingerBands(
        val upper: Double,
        val middle: Double,
        val lower: Double
    )
    
    override fun getName(): String = "BB($period,$deviations)"
    
    override fun calculate(prices: List<Double>): Double {
        val bands = calculateBands(prices)
        val currentPrice = prices.lastOrNull() ?: 0.0
        return (currentPrice - bands.lower) / (bands.upper - bands.lower)
    }
    
    override fun getSignal(prices: List<Double>): SignalType {
        if (prices.size < period) return SignalType.NEUTRAL
        
        val bands = calculateBands(prices)
        val currentPrice = prices.last()
        val previousPrice = prices[prices.size - 2]
        
        return when {
            previousPrice <= bands.lower && currentPrice > bands.lower -> SignalType.BUY
            previousPrice >= bands.upper && currentPrice < bands.upper -> SignalType.SELL
            else -> SignalType.NEUTRAL
        }
    }
    
    fun calculateBands(prices: List<Double>): BollingerBands {
        if (prices.size < period) {
            val price = prices.lastOrNull() ?: 0.0
            return BollingerBands(price, price, price)
        }
        
        val sma = prices.takeLast(period).average()
        val variance = prices.takeLast(period).map { (it - sma) * (it - sma) }.average()
        val stdDev = sqrt(variance)
        
        return BollingerBands(
            upper = sma + (stdDev * deviations),
            middle = sma,
            lower = sma - (stdDev * deviations)
        )
    }
}
