package com.mugiwara.presentation.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Accounts : Screen("accounts")
    object Markets : Screen("markets")
    object Trades : Screen("trades")
    object Settings : Screen("settings")
}
