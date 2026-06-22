package com.mugiwara.presentation.ui.screens.trades

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mugiwara.domain.model.Trade
import com.mugiwara.domain.model.TradeStatus
import com.mugiwara.presentation.ui.theme.LossRed
import com.mugiwara.presentation.ui.theme.ProfitGreen
import com.mugiwara.presentation.viewmodel.TradesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TradesScreen(
    viewModel: TradesViewModel = hiltViewModel()
) {
    val activeTrades by viewModel.activeTrades.collectAsStateWithLifecycle()
    val closedTrades by viewModel.closedTrades.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trades") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Active (${activeTrades.size})") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Closed (${closedTrades.size})") }
                )
            }

            val trades = if (selectedTab == 0) activeTrades else closedTrades

            if (trades.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (selectedTab == 0) "No active trades" else "No closed trades",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(trades, key = { it.id }) { trade ->
                        TradeCard(trade)
                    }
                }
            }
        }
    }
}

@Composable
private fun TradeCard(trade: Trade) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = trade.symbol,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = when (trade.status) {
                        TradeStatus.ACTIVE -> ProfitGreen.copy(alpha = 0.2f)
                        TradeStatus.CLOSED -> MaterialTheme.colorScheme.surface
                        TradeStatus.PENDING -> MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    }
                ) {
                    Text(
                        text = trade.status.name,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = when (trade.status) {
                            TradeStatus.ACTIVE -> ProfitGreen
                            TradeStatus.CLOSED -> MaterialTheme.colorScheme.onSurface
                            TradeStatus.PENDING -> MaterialTheme.colorScheme.primary
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = trade.type.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${String.format("%.2f", trade.lotSize)} lots @ ${String.format("%.5f", trade.entryPrice)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "SL: ${trade.stopLoss.takeIf { it > 0 }?.let { String.format("%.5f", it) } ?: "--"}",
                    style = MaterialTheme.typography.labelSmall,
                    color = LossRed
                )
                Text(
                    text = "TP: ${trade.takeProfit.takeIf { it > 0 }?.let { String.format("%.5f", it) } ?: "--"}",
                    style = MaterialTheme.typography.labelSmall,
                    color = ProfitGreen
                )
                Text(
                    text = "P/L: ${if (trade.profit >= 0) "+" else ""}$${String.format("%.2f", trade.profit)}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (trade.profit >= 0) ProfitGreen else LossRed
                )
            }
        }
    }
}
