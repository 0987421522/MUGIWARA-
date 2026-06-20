package com.mugiwara.service.analysis

class MovingAverageIndicator(private val period: Int = 20) : TechnicalIndicator {
    
    override fun getName(): String = "MA($period)"
    
    override fun calculate(prices: List<Double>): Double {
        if (prices.size < period) return prices.lastOrNull() ?: 0.0
        return prices.takeLast(period).average()
    }
    
    override fun getSignal(prices: List<Double>): SignalType {
        if (prices.size < period + 1) return SignalType.NEUTRAL
        
        val currentMA = calculate(prices)
        val previousMA = calculate(prices.dropLast(1))
        val currentPrice = prices.last()
        
        return when {
            currentPrice > currentMA && prices[prices.size - 2] <= previousMA -> SignalType.BUY
            currentPrice < currentMA && prices[prices.size - 2] >= previousMA -> SignalType.SELL
            else -> SignalType.NEUTRAL
        }
    }
}
