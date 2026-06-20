package com.mugiwara.utils

object Constants {
    const val DATABASE_NAME = "mugiwara_database"
    const val BASE_URL_MT5 = "https://mt5api.example.com"
    const val WORKER_TAG_MARKET = "market_analysis_worker"
    const val WORKER_TAG_TRADE = "trade_execution_worker"
    const val NOTIFICATION_CHANNEL_ID = "mugiwara_channel"
    const val NOTIFICATION_CHANNEL_NAME = "MUGIWARA Trading"
    const val SHARED_PREF_NAME = "mugiwara_prefs"
    const val KEY_AUTO_TRADING = "auto_trading_enabled"
    const val KEY_DARK_MODE = "dark_mode_enabled"
    const val KEY_NOTIFICATIONS = "notifications_enabled"
    const val DEFAULT_RISK_PERCENT = 2.0
    const val DEFAULT_STOP_LOSS_PIPS = 50
    const val DEFAULT_TAKE_PROFIT_PIPS = 100
    const val MARKET_OPEN_HOUR = 0
    const val MARKET_CLOSE_HOUR = 24
}
