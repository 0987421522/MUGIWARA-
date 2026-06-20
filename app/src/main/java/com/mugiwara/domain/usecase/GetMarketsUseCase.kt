package com.mugiwara.domain.usecase

import com.mugiwara.data.mapper.MarketMapper
import com.mugiwara.data.repository.MarketRepository
import com.mugiwara.domain.model.Market
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMarketsUseCase @Inject constructor(
    private val marketRepository: MarketRepository
) {
    operator fun invoke(): Flow<List<Market>> {
        return marketRepository.getAllMarkets().map { entities ->
            entities.map { MarketMapper.toDomain(it) }
        }
    }
}
