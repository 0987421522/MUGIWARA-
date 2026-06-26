package com.mugiwara.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mugiwara.data.repository.AccountRepository
import com.mugiwara.data.repository.TradeRepository
import com.mugiwara.data.repository.SettingsRepository
import com.mugiwara.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardData(
    val totalBalance: Double = 0.0,
    val totalProfit: Double = 0.0,
    val activeTrades: Int = 0,
    val winRate: Double = 0.0,
    val accountCount: Int = 0
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val tradeRepository: TradeRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<DashboardData>>(UiState.Idle)
    val uiState: StateFlow<UiState<DashboardData>> = _uiState.asStateFlow()

    private val _autoTrading = MutableStateFlow(false)
    val autoTrading: StateFlow<Boolean> = _autoTrading.asStateFlow()

    init {
        loadDashboard()
        observeAutoTrading()
    }

    private fun loadDashboard() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val accountCount = accountRepository.getAccountCount()
                val activeAccount = accountRepository.getActiveAccount().first()
                val balance = activeAccount?.balance ?: 0.0
                val totalProfit = tradeRepository.getTotalProfit()
                val activeTrades = tradeRepository.getActiveTradeCount()
                val winRate = tradeRepository.getWinRate()

                _uiState.value = UiState.Success(
                    DashboardData(
                        totalBalance = balance,
                        totalProfit = totalProfit,
                        activeTrades = activeTrades,
                        winRate = winRate,
                        accountCount = accountCount
                    )
                )
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun observeAutoTrading() {
        viewModelScope.launch {
            settingsRepository.getSettings().collect { entity ->
                _autoTrading.value = entity?.autoTrading ?: false
            }
        }
    }
}
