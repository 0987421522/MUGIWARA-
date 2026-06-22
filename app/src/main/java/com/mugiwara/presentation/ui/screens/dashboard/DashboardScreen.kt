package com.mugiwara.presentation.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mugiwara.presentation.ui.theme.LossRed
import com.mugiwara.presentation.ui.theme.ProfitGreen
import com.mugiwara.presentation.viewmodel.DashboardData
import com.mugiwara.presentation.viewmodel.DashboardViewModel
import com.mugiwara.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val autoTrading by viewModel.autoTrading.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mugiwara Dashboard") },
                actions = {
                    AutoTradingBadge(enabled = autoTrading)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        when (val state = uiState) {
            is UiState.Idle, is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item { BalanceCard(data = state.data) }
                    item { StatsGrid(data = state.data) }
                }
            }
        }
    }
}

@Composable
private fun AutoTradingBadge(enabled: Boolean) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = if (enabled) ProfitGreen else MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.padding(end = 16.dp)
    ) {
        Text(
            text = if (enabled) "\u25CF Auto" else "\u25CB Manual",
            style = MaterialTheme.typography.labelSmall,
            color = if (enabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun BalanceCard(data: DashboardData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total Balance",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$${String.format("%.2f", data.totalBalance)}",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Profit: ${if (data.totalProfit >= 0) "+" else ""}$${String.format("%.2f", data.totalProfit)}",
                style = MaterialTheme.typography.titleMedium,
                color = if (data.totalProfit >= 0) ProfitGreen else LossRed
            )
        }
    }
}

@Composable
private fun StatsGrid(data: DashboardData) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatCard(
            label = "Active Trades",
            value = data.activeTrades.toString(),
            modifier = Modifier.weight(1f)
        )
        StatCard(
            label = "Win Rate",
            value = "${String.format("%.1f", data.winRate)}%",
            modifier = Modifier.weight(1f)
        )
        StatCard(
            label = "Accounts",
            value = data.accountCount.toString(),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}
