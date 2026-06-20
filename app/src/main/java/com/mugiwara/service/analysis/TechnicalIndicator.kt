package com.mugiwara.service.analysis

interface TechnicalIndicator {
    fun calculate(prices: List<Double>): Double
    fun getName(): String
    fun getSignal(prices: List<Double>): SignalType
}

enum class SignalType {
    BUY, SELL, NEUTRAL
}
