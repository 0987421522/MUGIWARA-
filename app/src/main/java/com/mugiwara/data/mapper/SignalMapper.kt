package com.mugiwara.data.mapper

import com.mugiwara.data.model.SignalEntity
import com.mugiwara.domain.model.Signal
import com.mugiwara.domain.model.SignalDirection

object SignalMapper {
    fun toDomain(entity: SignalEntity): Signal {
        return Signal(
            id = entity.id,
            marketId = entity.marketId,
            symbol = entity.symbol,
            direction = SignalDirection.valueOf(entity.direction),
            strength = entity.strength,
            confidence = entity.confidence,
            entryPrice = entity.entryPrice,
            stopLoss = entity.stopLoss,
            takeProfit = entity.takeProfit,
            riskRewardRatio = entity.riskRewardRatio,
            timeFrame = entity.timeFrame,
            isExecuted = entity.isExecuted,
            isFiltered = entity.isFiltered
        )
    }
    
    fun toEntity(domain: Signal): SignalEntity {
        return SignalEntity(
            id = domain.id,
            marketId = domain.marketId,
            symbol = domain.symbol,
            type = domain.direction.name,
            direction = domain.direction.name,
            strength = domain.strength,
            confidence = domain.confidence,
            entryPrice = domain.entryPrice,
            stopLoss = domain.stopLoss,
            takeProfit = domain.takeProfit,
            riskRewardRatio = domain.riskRewardRatio,
            indicators = "",
            timeFrame = domain.timeFrame,
            isExecuted = domain.isExecuted,
            isFiltered = domain.isFiltered,
            reason = null,
            createdAt = System.currentTimeMillis(),
            expiresAt = System.currentTimeMillis() + 3600000
        )
    }
}
