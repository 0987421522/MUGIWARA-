package com.mugiwara.domain.usecase

import com.mugiwara.data.mapper.SettingsMapper
import com.mugiwara.data.repository.SettingsRepository
import com.mugiwara.domain.model.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<Settings?> {
        return settingsRepository.getSettings().map { entity ->
            entity?.let { SettingsMapper.toDomain(it) }
        }
    }
}
