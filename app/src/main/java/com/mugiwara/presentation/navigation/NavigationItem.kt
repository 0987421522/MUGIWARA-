package com.mugiwara.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val screen: Screen,
    val title: String,
    val icon: ImageVector,
    val route: String = screen.route
) {
    companion object {
        val items = listOf(
            NavigationItem(Screen.Dashboard, "Dashboard", Icons.Default.Home),
            NavigationItem(Screen.Accounts, "Accounts", Icons.Default.AccountBalance),
            NavigationItem(Screen.Markets, "Markets", Icons.Default.ShowChart),
            NavigationItem(Screen.Trades, "Trades", Icons.Default.Assessment),
            NavigationItem(Screen.Settings, "Settings", Icons.Default.Settings)
        )
    }
}
