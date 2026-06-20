package com.mugiwara.domain.usecase

import com.mugiwara.data.mapper.SettingsMapper
import com.mugiwara.data.repository.SettingsRepository
import com.mugiwara.domain.model.Settings
import javax.inject.Inject

class SaveSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(settings: Settings) {
        settingsRepository.saveSettings(SettingsMapper.toEntity(settings))
    }
}
