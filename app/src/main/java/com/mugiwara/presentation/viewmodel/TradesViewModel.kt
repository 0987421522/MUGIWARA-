package com.mugiwara.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mugiwara.data.mapper.TradeMapper
import com.mugiwara.data.repository.TradeRepository
import com.mugiwara.domain.model.Trade
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TradesViewModel @Inject constructor(
    private val tradeRepository: TradeRepository
) : ViewModel() {

    private val _activeTrades = MutableStateFlow<List<Trade>>(emptyList())
    val activeTrades: StateFlow<List<Trade>> = _activeTrades.asStateFlow()

    private val _closedTrades = MutableStateFlow<List<Trade>>(emptyList())
    val closedTrades: StateFlow<List<Trade>> = _closedTrades.asStateFlow()

    init {
        loadTrades()
    }

    private fun loadTrades() {
        viewModelScope.launch {
            tradeRepository.getActiveTrades().collect { entities ->
                _activeTrades.value = entities.map { TradeMapper.toDomain(it) }
            }
        }
        viewModelScope.launch {
            tradeRepository.getClosedTrades().collect { entities ->
                _closedTrades.value = entities.map { TradeMapper.toDomain(it) }
            }
        }
    }
}
