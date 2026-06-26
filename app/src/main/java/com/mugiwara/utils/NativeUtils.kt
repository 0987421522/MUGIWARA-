package com.mugiwara.utils

/**
 * Utility for native JNI calls
 */
object NativeUtils {
    
    init {
        try {
            System.loadLibrary("mugiwara_native")
        } catch (e: UnsatisfiedLinkError) {
            e.printStackTrace()
        }
    }
    
    external fun stringFromJNI(): String
    
    external fun calculateRSI(prices: DoubleArray): Double
    
    external fun calculateMACD(prices: DoubleArray): Double
    
    external fun calculateMA(prices: DoubleArray, period: Int): Double
    
    external fun calculateBollingerBands(prices: DoubleArray, period: Int, deviations: Double): Double
    
    external fun getNativeTimestamp(): Long
    
    external fun benchmarkAnalysis(prices: DoubleArray): Double
}
