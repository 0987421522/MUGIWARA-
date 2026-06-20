package com.mugiwara.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mugiwara.domain.model.Trade
import com.mugiwara.domain.usecase.GetActiveTradesUseCase
import com.mugiwara.domain.usecase.GetTradesUseCase
import com.mugiwara.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TradesViewModel @Inject constructor(
    private val getTradesUseCase: GetTradesUseCase,
    private val getActiveTradesUseCase: GetActiveTradesUseCase
) : ViewModel() {

    private val _trades = MutableStateFlow<UiState<List<Trade>>>(UiState.Loading)
    val trades: StateFlow<UiState<List<Trade>>> = _trades.asStateFlow()

    private val _activeTrades = MutableStateFlow<UiState<List<Trade>>>(UiState.Loading)
    val activeTrades: StateFlow<UiState<List<Trade>>> = _activeTrades.asStateFlow()

    private val _showActiveOnly = MutableStateFlow(false)
    val showActiveOnly: StateFlow<Boolean> = _showActiveOnly.asStateFlow()

    init {
        loadTrades()
        loadActiveTrades()
    }

    fun loadTrades() {
        viewModelScope.launch {
            getTradesUseCase()
                .onStart { _trades.value = UiState.Loading }
                .catch { e -> _trades.value = UiState.Error(e.message ?: "Unknown error") }
                .collect { trades ->
                    _trades.value = UiState.Success(trades)
                }
        }
    }

    fun loadActiveTrades() {
        viewModelScope.launch {
            getActiveTradesUseCase()
                .onStart { _activeTrades.value = UiState.Loading }
                .catch { e -> _activeTrades.value = UiState.Error(e.message ?: "Unknown error") }
                .collect { trades ->
                    _activeTrades.value = UiState.Success(trades)
                }
        }
    }

    fun toggleActiveOnly() {
        _showActiveOnly.value = !_showActiveOnly.value
    }

    fun refreshTrades() {
        loadTrades()
        loadActiveTrades()
    }
}
