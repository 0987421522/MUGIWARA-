package com.mugiwara.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mugiwara.R

data class Trade(
    val id: String,
    val symbol: String,
    val type: String,
    val entryPrice: Double,
    val currentPrice: Double,
    val lotSize: Double,
    val stopLoss: Double,
    val takeProfit: Double,
    val profit: Double,
    val isActive: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TradesScreen() {
    val trades = listOf(
        Trade("1", "EUR/USD", "BUY", 1.0820, 1.0856, 0.1, 1.0780, 1.0900, 36.0, true),
        Trade("2", "GBP/JPY", "SELL", 183.50, 182.34, 0.05, 184.20, 181.00, 58.0, true),
        Trade("3", "XAU/USD", "BUY", 2025.00, 2034.50, 0.02, 2015.00, 2050.00, 19.0, true),
        Trade("4", "BTC/USD", "SELL", 45500.00, 45230.00, 0.01, 46000.00, 44000.00, 27.0, false),
        Trade("5", "ETH/USD", "BUY", 2420.00, 2450.75, 0.05, 2380.00, 2500.00, 15.38, false)
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.nav_trades),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(trades) { trade ->
                TradeCard(trade = trade)
            }
        }
    }
}

@Composable
fun TradeCard(trade: Trade) {
    val profitColor = if (trade.profit >= 0) Color(0xFF4CAF50) else Color(0xFFCF6679)
    val typeColor = if (trade.type == "BUY") Color(0xFF4CAF50) else Color(0xFFCF6679)
    val statusText = if (trade.isActive) "Active" else "Closed"
    val statusColor = if (trade.isActive) Color(0xFF4CAF50) else Color(0xFFB0B0B0)
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = trade.symbol,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Card(
                        colors = CardDefaults.cardColors(containerColor = typeColor.copy(alpha = 0.2f))
                    ) {
                        Text(
                            text = trade.type,
                            style = MaterialTheme.typography.labelMedium,
                            color = typeColor,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                
                Text(
                    text = statusText,
                    style = MaterialTheme.typography.labelMedium,
                    color = statusColor
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Entry: ${String.format("%.5f", trade.entryPrice)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = "Current: ${String.format("%.5f", trade.currentPrice)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Lot: ${trade.lotSize}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = "$${String.format("%.2f", trade.profit)}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = profitColor
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "SL: ${String.format("%.5f", trade.stopLoss)}",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFFCF6679)
                )
                
                Text(
                    text = "TP: ${String.format("%.5f", trade.takeProfit)}",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFF4CAF50)
                )
            }
        }
    }
}
