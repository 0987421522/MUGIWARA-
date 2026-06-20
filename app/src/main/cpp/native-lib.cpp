#include <jni.h>
#include <string>
#include <vector>
#include "indicators/rsi.h"
#include "indicators/macd.h"
#include "indicators/moving_average.h"
#include "indicators/bollinger_bands.h"
#include "analysis/market_analyzer.h"
#include "utils/performance.h"

extern "C" {

JNIEXPORT jstring JNICALL
Java_com_mugiwara_utils_NativeUtils_stringFromJNI(JNIEnv* env, jobject /* this */) {
    std::string hello = "MUGIWARA Native Engine v1.0";
    return env->NewStringUTF(hello.c_str());
}

JNIEXPORT jdouble JNICALL
Java_com_mugiwara_utils_NativeUtils_calculateRSI(JNIEnv* env, jobject /* this */, jdoubleArray prices) {
    jsize len = env->GetArrayLength(prices);
    jdouble* elements = env->GetDoubleArrayElements(prices, nullptr);
    
    std::vector<double> priceVector(elements, elements + len);
    
    mugiwara::RSIIndicator rsi(14);
    double result = rsi.calculate(priceVector);
    
    env->ReleaseDoubleArrayElements(prices, elements, 0);
    return result;
}

JNIEXPORT jdouble JNICALL
Java_com_mugiwara_utils_NativeUtils_calculateMACD(JNIEnv* env, jobject /* this */, jdoubleArray prices) {
    jsize len = env->GetArrayLength(prices);
    jdouble* elements = env->GetDoubleArrayElements(prices, nullptr);
    
    std::vector<double> priceVector(elements, elements + len);
    
    mugiwara::MACDIndicator macd(12, 26, 9);
    double result = macd.calculate(priceVector);
    
    env->ReleaseDoubleArrayElements(prices, elements, 0);
    return result;
}

JNIEXPORT jdouble JNICALL
Java_com_mugiwara_utils_NativeUtils_calculateMA(JNIEnv* env, jobject /* this */, jdoubleArray prices, jint period) {
    jsize len = env->GetArrayLength(prices);
    jdouble* elements = env->GetDoubleArrayElements(prices, nullptr);
    
    std::vector<double> priceVector(elements, elements + len);
    
    mugiwara::MovingAverageIndicator ma(period);
    double result = ma.calculate(priceVector);
    
    env->ReleaseDoubleArrayElements(prices, elements, 0);
    return result;
}

JNIEXPORT jdouble JNICALL
Java_com_mugiwara_utils_NativeUtils_calculateBollingerBands(JNIEnv* env, jobject /* this */, jdoubleArray prices, jint period, jdouble deviations) {
    jsize len = env->GetArrayLength(prices);
    jdouble* elements = env->GetDoubleArrayElements(prices, nullptr);
    
    std::vector<double> priceVector(elements, elements + len);
    
    mugiwara::BollingerBandsIndicator bb(period, deviations);
    double result = bb.calculate(priceVector);
    
    env->ReleaseDoubleArrayElements(prices, elements, 0);
    return result;
}

JNIEXPORT jlong JNICALL
Java_com_mugiwara_utils_NativeUtils_getNativeTimestamp(JNIEnv* env, jobject /* this */) {
    return mugiwara::PerformanceMonitor::getCurrentTimestamp();
}

JNIEXPORT jdouble JNICALL
Java_com_mugiwara_utils_NativeUtils_benchmarkAnalysis(JNIEnv* env, jobject /* this */, jdoubleArray prices) {
    jsize len = env->GetArrayLength(prices);
    jdouble* elements = env->GetDoubleArrayElements(prices, nullptr);
    
    std::vector<double> priceVector(elements, elements + len);
    
    mugiwara::PerformanceMonitor monitor;
    monitor.start();
    
    mugiwara::MarketAnalyzer analyzer;
    auto signals = analyzer.analyzeMarket("BENCHMARK", priceVector);
    
    monitor.stop();
    
    env->ReleaseDoubleArrayElements(prices, elements, 0);
    return monitor.getElapsedMs();
}

}
