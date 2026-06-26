package com.mugiwara.utils

object Constants {
    const val BASE_URL_MT5 = "http://10.0.2.2:8080/api/v1/"
    const val APP_NAME = "Mugiwara"
    const val APP_VERSION = "1.0.0"
    const val DATABASE_NAME = "mugiwara_database"
    const val DATABASE_VERSION = 1

    const val DEFAULT_RISK_PERCENT = 1.0
    const val DEFAULT_STOP_LOSS_PIPS = 20
    const val DEFAULT_TAKE_PROFIT_PIPS = 40
    const val MAX_DAILY_TRADES = 10
    const val MAX_DAILY_LOSS = 100.0
    const val MIN_BALANCE = 100.0

    const val FOREX_SESSION_OPEN = 86400000L
    const val FOREX_SESSION_CLOSE = 86400000L

    const val UPDATE_INTERVAL_MS = 5000L
    const val ANALYSIS_INTERVAL_MS = 60000L

    const val RSI_OVERSOLD = 30
    const val RSI_OVERBOUGHT = 70
    const val MACD_FAST = 12
    const val MACD_SLOW = 26
    const val MACD_SIGNAL = 9
    const val MOVING_AVERAGE_PERIOD = 20
    const val BOLLINGER_PERIOD = 20
    const val BOLLINGER_STD_DEV = 2.0

    const val NOTIFICATION_CHANNEL_ID = "mugiwara_trading"
    const val NOTIFICATION_CHANNEL_NAME = "Mugiwara Trading"

    // Added missing constant
    const val SHARED_PREF_NAME = "mugiwara_prefs"
}
