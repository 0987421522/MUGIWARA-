package com.mugiwara.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mugiwara.data.mapper.SettingsMapper
import com.mugiwara.data.repository.SettingsRepository
import com.mugiwara.domain.model.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _settings = MutableStateFlow(Settings())
    val settings: StateFlow<Settings> = _settings.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            settingsRepository.getSettings().collect { entity ->
                if (entity != null) {
                    _settings.value = SettingsMapper.toDomain(entity)
                }
            }
        }
    }

    fun updateSettings(newSettings: Settings) {
        viewModelScope.launch {
            settingsRepository.saveSettings(SettingsMapper.toEntity(newSettings))
            _settings.value = newSettings
        }
    }

    fun toggleAutoTrading() {
        viewModelScope.launch {
            val updated = _settings.value.copy(autoTrading = !_settings.value.autoTrading)
            updateSettings(updated)
        }
    }

    fun toggleNotifications() {
        viewModelScope.launch {
            val updated = _settings.value.copy(notifications = !_settings.value.notifications)
            updateSettings(updated)
        }
    }

    fun updateRiskPercent(percent: Double) {
        viewModelScope.launch {
            val updated = _settings.value.copy(defaultRiskPercent = percent)
            updateSettings(updated)
        }
    }

    fun updateMaxDailyLoss(loss: Double) {
        viewModelScope.launch {
            val updated = _settings.value.copy(maxDailyLoss = loss)
            updateSettings(updated)
        }
    }
}
