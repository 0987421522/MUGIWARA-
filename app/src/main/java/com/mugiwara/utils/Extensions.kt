package com.mugiwara.utils

import android.util.Log

/**
 * Logging utility for consistent app-wide logging
 */
object AppLogger {
    
    private const val TAG = "MUGIWARA"
    
    fun d(tag: String, message: String) {
        Log.d("$TAG:$tag", message)
    }
    
    fun i(tag: String, message: String) {
        Log.i("$TAG:$tag", message)
    }
    
    fun w(tag: String, message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            Log.w("$TAG:$tag", message, throwable)
        } else {
            Log.w("$TAG:$tag", message)
        }
    }
    
    fun e(tag: String, message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            Log.e("$TAG:$tag", message, throwable)
        } else {
            Log.e("$TAG:$tag", message)
        }
    }
}

/**
 * Extension functions for Result type
 */
inline fun <T> Result<T>.getOrElse(onFailure: (Exception) -> T): T {
    return when (this) {
        is Result.Success -> data
        is Result.Error -> onFailure(exception)
        is Result.Loading -> throw Exception("Still loading")
    }
}

inline fun <T> Result<T>.fold(
    onSuccess: (T) -> Unit,
    onFailure: (Exception) -> Unit
) {
    when (this) {
        is Result.Success -> onSuccess(data)
        is Result.Error -> onFailure(exception)
        is Result.Loading -> {}
    }
}
