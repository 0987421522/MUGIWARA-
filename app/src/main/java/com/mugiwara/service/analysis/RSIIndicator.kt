package com.mugiwara.service.analysis

class RSIIndicator(private val period: Int = 14) : TechnicalIndicator {
    
    override fun getName(): String = "RSI($period)"
    
    override fun calculate(prices: List<Double>): Double {
        if (prices.size < period + 1) return 50.0
        
        var gains = 0.0
        var losses = 0.0
        
        for (i in prices.size - period until prices.size) {
            val change = prices[i] - prices[i - 1]
            if (change > 0) gains += change
            else losses += kotlin.math.abs(change)
        }
        
        val avgGain = gains / period
        val avgLoss = losses / period
        
        if (avgLoss == 0.0) return 100.0
        
        val rs = avgGain / avgLoss
        return 100.0 - (100.0 / (1.0 + rs))
    }
    
    override fun getSignal(prices: List<Double>): SignalType {
        val rsi = calculate(prices)
        return when {
            rsi > 70 -> SignalType.SELL
            rsi < 30 -> SignalType.BUY
            else -> SignalType.NEUTRAL
        }
    }
}
