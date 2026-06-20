package com.mugiwara.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mugiwara.domain.model.Market
import com.mugiwara.domain.usecase.GetMarketsUseCase
import com.mugiwara.domain.usecase.GetOpenMarketsUseCase
import com.mugiwara.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketsViewModel @Inject constructor(
    private val getMarketsUseCase: GetMarketsUseCase,
    private val getOpenMarketsUseCase: GetOpenMarketsUseCase
) : ViewModel() {

    private val _markets = MutableStateFlow<UiState<List<Market>>>(UiState.Loading)
    val markets: StateFlow<UiState<List<Market>>> = _markets.asStateFlow()

    private val _openMarkets = MutableStateFlow<UiState<List<Market>>>(UiState.Loading)
    val openMarkets: StateFlow<UiState<List<Market>>> = _openMarkets.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    init {
        loadMarkets()
        loadOpenMarkets()
    }

    fun loadMarkets() {
        viewModelScope.launch {
            getMarketsUseCase()
                .onStart { _markets.value = UiState.Loading }
                .catch { e -> _markets.value = UiState.Error(e.message ?: "Unknown error") }
                .collect { markets ->
                    _markets.value = UiState.Success(markets)
                }
        }
    }

    fun loadOpenMarkets() {
        viewModelScope.launch {
            getOpenMarketsUseCase()
                .onStart { _openMarkets.value = UiState.Loading }
                .catch { e -> _openMarkets.value = UiState.Error(e.message ?: "Unknown error") }
                .collect { markets ->
                    _openMarkets.value = UiState.Success(markets)
                }
        }
    }

    fun selectCategory(category: String?) {
        _selectedCategory.value = category
    }

    fun refreshMarkets() {
        loadMarkets()
        loadOpenMarkets()
    }
}
