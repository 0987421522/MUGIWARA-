package com.mugiwara.utils

object NativeUtils {
    init {
        System.loadLibrary("mugiwara_native")
    }
    
    external fun stringFromJNI(): String
    external fun calculateRSI(prices: DoubleArray): Double
    external fun calculateMACD(prices: DoubleArray): Double
    external fun calculateMA(prices: DoubleArray, period: Int): Double
    external fun calculateBollingerBands(prices: DoubleArray, period: Int, deviations: Double): Double
    external fun getNativeTimestamp(): Long
    external fun benchmarkAnalysis(prices: DoubleArray): Double
    
    fun isNativeLibraryLoaded(): Boolean {
        return try {
            stringFromJNI()
            true
        } catch (e: UnsatisfiedLinkError) {
            false
        }
    }
}
