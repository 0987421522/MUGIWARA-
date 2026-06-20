package com.mugiwara.presentation.ui

sealed class Screen(val route: String) {
    data object Dashboard : Screen("dashboard")
    data object Accounts : Screen("accounts")
    data object AddAccount : Screen("add_account")
    data object Markets : Screen("markets")
    data object MarketDetail : Screen("market_detail/{marketId}") {
        fun createRoute(marketId: String) = "market_detail/$marketId"
    }
    data object Trades : Screen("trades")
    data object TradeDetail : Screen("trade_detail/{tradeId}") {
        fun createRoute(tradeId: String) = "trade_detail/$tradeId"
    }
    data object Settings : Screen("settings")
}
