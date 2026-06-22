package com.mugiwara.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mugiwara.data.mapper.MarketMapper
import com.mugiwara.data.repository.MarketRepository
import com.mugiwara.domain.model.Market
import com.mugiwara.utils.Result
import com.mugiwara.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketsViewModel @Inject constructor(
    private val marketRepository: MarketRepository
) : ViewModel() {

    private val _markets = MutableStateFlow<List<Market>>(emptyList())
    val markets: StateFlow<List<Market>> = _markets.asStateFlow()

    private val _fetchState = MutableStateFlow<UiState<List<Market>>>(UiState.Idle)
    val fetchState: StateFlow<UiState<List<Market>>> = _fetchState.asStateFlow()

    init {
        loadMarkets()
    }

    private fun loadMarkets() {
        viewModelScope.launch {
            marketRepository.getAllMarkets().collect { entities ->
                _markets.value = entities.map { MarketMapper.toDomain(it) }
            }
        }
    }

    fun fetchPrices(token: String) {
        viewModelScope.launch {
            _fetchState.value = UiState.Loading
            val result = marketRepository.fetchMarketPrices(token)
            when (result) {
                is Result.Success -> {
                    val markets = result.data.map { MarketMapper.toDomain(it) }
                    _fetchState.value = UiState.Success(markets)
                }
                is Result.Error -> {
                    _fetchState.value = UiState.Error(result.message)
                }
            }
        }
    }
}
