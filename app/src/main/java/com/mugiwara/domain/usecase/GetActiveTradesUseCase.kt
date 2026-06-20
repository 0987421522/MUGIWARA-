package com.mugiwara.domain.usecase

import com.mugiwara.data.mapper.TradeMapper
import com.mugiwara.data.repository.TradeRepository
import com.mugiwara.domain.model.Trade
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetActiveTradesUseCase @Inject constructor(
    private val tradeRepository: TradeRepository
) {
    operator fun invoke(): Flow<List<Trade>> {
        return tradeRepository.getActiveTrades().map { entities ->
            entities.map { TradeMapper.toDomain(it) }
        }
    }
}
