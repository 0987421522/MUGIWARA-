package com.mugiwara.presentation.ui.screens.markets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mugiwara.domain.model.Market
import com.mugiwara.presentation.ui.theme.LossRed
import com.mugiwara.presentation.ui.theme.ProfitGreen
import com.mugiwara.presentation.viewmodel.MarketsViewModel
import com.mugiwara.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketsScreen(
    viewModel: MarketsViewModel = hiltViewModel()
) {
    val markets by viewModel.markets.collectAsStateWithLifecycle()
    val fetchState by viewModel.fetchState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Markets") },
                actions = {
                    IconButton(onClick = { /* need token from active account */ }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        when (fetchState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            else -> {
                if (markets.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No market data available",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(padding),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(markets, key = { it.id }) { market ->
                            MarketCard(market)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MarketCard(market: Market) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = market.symbol,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = market.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$${String.format("%.5f", market.price)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${if (market.change >= 0) "+" else ""}${String.format("%.2f", market.change)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (market.change >= 0) ProfitGreen else LossRed
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "(${String.format("%.2f", market.changePercent)}%)",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (market.changePercent >= 0) ProfitGreen else LossRed
                    )
                }
                Text(
                    text = "Bid: ${String.format("%.5f", market.bid)} / Ask: ${String.format("%.5f", market.ask)}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}
