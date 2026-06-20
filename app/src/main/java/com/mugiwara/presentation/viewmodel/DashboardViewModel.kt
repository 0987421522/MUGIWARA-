package com.mugiwara.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mugiwara.data.repository.TradeRepository
import com.mugiwara.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardStats(
    val totalProfit: Double,
    val activeTrades: Int,
    val winRate: Double,
    val balance: Double
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val tradeRepository: TradeRepository
) : ViewModel() {

    private val _stats = MutableStateFlow<UiState<DashboardStats>>(UiState.Loading)
    val stats: StateFlow<UiState<DashboardStats>> = _stats.asStateFlow()

    init {
        loadStats()
    }

    fun loadStats() {
        viewModelScope.launch {
            _stats.value = UiState.Loading
            try {
                val totalProfit = tradeRepository.getTotalProfit()
                val activeTrades = tradeRepository.getActiveTradeCount()
                val winRate = tradeRepository.getWinRate()
                val balance = 12450.00 // TODO: Get from active account
                
                val stats = DashboardStats(
                    totalProfit = totalProfit,
                    activeTrades = activeTrades,
                    winRate = winRate,
                    balance = balance
                )
                _stats.value = UiState.Success(stats)
            } catch (e: Exception) {
                _stats.value = UiState.Error(e.message ?: "Failed to load stats")
            }
        }
    }

    fun refreshStats() {
        loadStats()
    }
}
