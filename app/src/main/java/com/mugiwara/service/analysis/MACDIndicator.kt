package com.mugiwara.service.analysis

class MACDIndicator(
    private val fastPeriod: Int = 12,
    private val slowPeriod: Int = 26,
    private val signalPeriod: Int = 9
) : TechnicalIndicator {
    
    override fun getName(): String = "MACD($fastPeriod,$slowPeriod,$signalPeriod)"
    
    override fun calculate(prices: List<Double>): Double {
        if (prices.size < slowPeriod + signalPeriod) return 0.0
        
        val emaFast = calculateEMA(prices, fastPeriod)
        val emaSlow = calculateEMA(prices, slowPeriod)
        
        return emaFast - emaSlow
    }
    
    override fun getSignal(prices: List<Double>): SignalType {
        if (prices.size < slowPeriod + signalPeriod + 1) return SignalType.NEUTRAL
        
        val currentPrices = prices
        val previousPrices = prices.dropLast(1)
        
        val currentMACD = calculate(currentPrices)
        val previousMACD = calculate(previousPrices)
        
        val currentSignal = calculateSignal(currentPrices)
        val previousSignal = calculateSignal(previousPrices)
        
        return when {
            currentMACD > currentSignal && previousMACD <= previousSignal -> SignalType.BUY
            currentMACD < currentSignal && previousMACD >= previousSignal -> SignalType.SELL
            else -> SignalType.NEUTRAL
        }
    }
    
    private fun calculateEMA(prices: List<Double>, period: Int): Double {
        val multiplier = 2.0 / (period + 1)
        var ema = prices.take(period).average()
        
        for (i in period until prices.size) {
            ema = (prices[i] - ema) * multiplier + ema
        }
        
        return ema
    }
    
    private fun calculateSignal(prices: List<Double>): Double {
        val macdLine = mutableListOf<Double>()
        
        for (i in slowPeriod until prices.size) {
            val fastEMA = calculateEMA(prices.take(i + 1), fastPeriod)
            val slowEMA = calculateEMA(prices.take(i + 1), slowPeriod)
            macdLine.add(fastEMA - slowEMA)
        }
        
        return macdLine.takeLast(signalPeriod).average()
    }
}
