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

data class Market(
    val id: String,
    val name: String,
    val symbol: String,
    val category: String,
    val price: Double,
    val change: Double,
    val isOpen: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketsScreen() {
    val markets = listOf(
        Market("1", "EUR/USD", "EURUSD", stringResource(R.string.forex), 1.0856, 0.25, true),
        Market("2", "GBP/JPY", "GBPJPY", stringResource(R.string.forex), 182.34, -0.15, true),
        Market("3", "Gold", "XAUUSD", stringResource(R.string.gold), 2034.50, 1.20, true),
        Market("4", "Silver", "XAGUSD", stringResource(R.string.silver), 22.85, 0.80, true),
        Market("5", "Bitcoin", "BTCUSD", stringResource(R.string.crypto), 45230.00, 2.50, true),
        Market("6", "Ethereum", "ETHUSD", stringResource(R.string.crypto), 2450.75, 1.80, true),
        Market("7", "S&P 500", "US500", stringResource(R.string.indices), 4785.20, 0.45, false),
        Market("8", "Apple", "AAPL", stringResource(R.string.stocks), 185.30, 0.90, false)
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.nav_markets),
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
            items(markets) { market ->
                MarketCard(market = market)
            }
        }
    }
}

@Composable
fun MarketCard(market: Market) {
    val changeColor = if (market.change >= 0) Color(0xFF4CAF50) else Color(0xFFCF6679)
    val changeSign = if (market.change >= 0) "+" else ""
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = { }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = market.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Text(
                        text = market.symbol,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                MarketStatusBadge(isOpen = market.isOpen)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = String.format("%.5f", market.price),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Text(
                    text = "$changeSign${market.change}%",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = changeColor
                )
            }
            
            Text(
                text = market.category,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun MarketStatusBadge(isOpen: Boolean) {
    val color = if (isOpen) Color(0xFF4CAF50) else Color(0xFFFFC107)
    val text = if (isOpen) stringResource(R.string.market_open) else stringResource(R.string.market_closed)
    
    Card(
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.2f))
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = color,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
