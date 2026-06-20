package com.mugiwara.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mugiwara.domain.model.Settings
import com.mugiwara.domain.usecase.GetSettingsUseCase
import com.mugiwara.domain.usecase.SaveSettingsUseCase
import com.mugiwara.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase
) : ViewModel() {

    private val _settings = MutableStateFlow<UiState<Settings>>(UiState.Loading)
    val settings: StateFlow<UiState<Settings>> = _settings.asStateFlow()

    private val _saveResult = MutableSharedFlow<String>()
    val saveResult: SharedFlow<String> = _saveResult.asSharedFlow()

    init {
        loadSettings()
    }

    fun loadSettings() {
        viewModelScope.launch {
            getSettingsUseCase()
                .onStart { _settings.value = UiState.Loading }
                .catch { e -> _settings.value = UiState.Error(e.message ?: "Unknown error") }
                .collect { settings ->
                    if (settings != null) {
                        _settings.value = UiState.Success(settings)
                    } else {
                        _settings.value = UiState.Error("Settings not found")
                    }
                }
        }
    }

    fun saveSettings(settings: Settings) {
        viewModelScope.launch {
            try {
                saveSettingsUseCase(settings)
                _saveResult.emit("Settings saved successfully")
                loadSettings()
            } catch (e: Exception) {
                _saveResult.emit("Failed to save settings: ${e.message}")
            }
        }
    }

    fun updateAutoTrading(enabled: Boolean) {
        val current = (_settings.value as? UiState.Success<Settings>)?.data ?: return
        saveSettings(current.copy(autoTrading = enabled))
    }

    fun updateNotifications(enabled: Boolean) {
        val current = (_settings.value as? UiState.Success<Settings>)?.data ?: return
        saveSettings(current.copy(notifications = enabled))
    }

    fun updateDarkMode(enabled: Boolean) {
        val current = (_settings.value as? UiState.Success<Settings>)?.data ?: return
        saveSettings(current.copy(darkMode = enabled))
    }

    fun updateRiskManagement(enabled: Boolean) {
        val current = (_settings.value as? UiState.Success<Settings>)?.data ?: return
        saveSettings(current.copy(riskManagement = enabled))
    }

    fun updateRiskPercent(percent: Double) {
        val current = (_settings.value as? UiState.Success<Settings>)?.data ?: return
        saveSettings(current.copy(defaultRiskPercent = percent))
    }

    fun updateMaxDailyTrades(count: Int) {
        val current = (_settings.value as? UiState.Success<Settings>)?.data ?: return
        saveSettings(current.copy(maxDailyTrades = count))
    }
}
