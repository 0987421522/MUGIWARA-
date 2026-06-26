package com.mugiwara.presentation.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mugiwara.presentation.ui.theme.ProfitGreen
import com.mugiwara.presentation.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.settings.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Auto Trading
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionTitle("Trading")
                    Spacer(modifier = Modifier.height(8.dp))
                    SettingsSwitch(
                        title = "Auto Trading",
                        subtitle = "Enable automated trade execution",
                        checked = settings.autoTrading,
                        onCheckedChange = { viewModel.toggleAutoTrading() }
                    )
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    SettingsSwitch(
                        title = "Notifications",
                        subtitle = "Receive trade alerts and updates",
                        checked = settings.notifications,
                        onCheckedChange = { viewModel.toggleNotifications() }
                    )
                }
            }

            // Risk Management
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionTitle("Risk Management")
                    Spacer(modifier = Modifier.height(8.dp))
                    SettingsSlider(
                        title = "Default Risk",
                        subtitle = "${settings.defaultRiskPercent}% per trade",
                        value = settings.defaultRiskPercent.toFloat(),
                        onValueChange = { viewModel.updateRiskPercent(it.toDouble()) },
                        valueRange = 0.1f..5.0f
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SettingsSlider(
                        title = "Max Daily Loss",
                        subtitle = "$${String.format("%.0f", settings.maxDailyLoss)}",
                        value = settings.maxDailyLoss.toFloat(),
                        onValueChange = { viewModel.updateMaxDailyLoss(it.toDouble()) },
                        valueRange = 10f..1000f
                    )
                }
            }

            // Trading Parameters
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionTitle("Parameters")
                    Spacer(modifier = Modifier.height(8.dp))
                    ParamRow("Stop Loss", "${settings.defaultStopLossPips} pips")
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    ParamRow("Take Profit", "${settings.defaultTakeProfitPips} pips")
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    ParamRow("Max Daily Trades", "${settings.maxDailyTrades}")
                    if (settings.trailingStopEnabled) {
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        ParamRow("Trailing Stop", "${settings.trailingStopPips} pips")
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun SettingsSwitch(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
private fun SettingsSlider(
    title: String,
    subtitle: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = ProfitGreen,
                fontWeight = FontWeight.Bold
            )
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ParamRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}
