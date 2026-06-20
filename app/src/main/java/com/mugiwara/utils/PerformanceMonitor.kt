package com.mugiwara.utils

import android.os.SystemClock

class PerformanceMonitor {
    private var startTime: Long = 0
    private var endTime: Long = 0
    private var isRunning = false
    
    fun start() {
        startTime = SystemClock.elapsedRealtime()
        isRunning = true
    }
    
    fun stop() {
        endTime = SystemClock.elapsedRealtime()
        isRunning = false
    }
    
    fun getElapsedMs(): Long {
        return if (isRunning) {
            SystemClock.elapsedRealtime() - startTime
        } else {
            endTime - startTime
        }
    }
    
    companion object {
        fun measure(block: () -> Unit): Long {
            val monitor = PerformanceMonitor()
            monitor.start()
            block()
            monitor.stop()
            return monitor.getElapsedMs()
        }
    }
}
