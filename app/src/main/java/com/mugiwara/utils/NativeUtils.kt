package com.mugiwara.utils

object NativeUtils {
    init {
        System.loadLibrary("mugiwara_native")
    }
    
    external fun stringFromJNI(): String
}
