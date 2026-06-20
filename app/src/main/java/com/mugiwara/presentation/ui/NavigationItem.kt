package com.mugiwara.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Dashboard : NavigationItem("dashboard", "Dashboard", Icons.Filled.Dashboard)
    data object Accounts : NavigationItem("accounts", "Accounts", Icons.Filled.AccountBalance)
    data object Markets : NavigationItem("markets", "Markets", Icons.Filled.TrendingUp)
    data object Trades : NavigationItem("trades", "Trades", Icons.Filled.SwapHoriz)
    data object Settings : NavigationItem("settings", "Settings", Icons.Filled.Settings)

    companion object {
        val items = listOf(Dashboard, Accounts, Markets, Trades, Settings)
    }
}
