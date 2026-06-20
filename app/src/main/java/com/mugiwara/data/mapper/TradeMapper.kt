package com.mugiwara.data.mapper

import com.mugiwara.data.model.TradeEntity
import com.mugiwara.domain.model.Trade
import com.mugiwara.domain.model.TradeStatus
import com.mugiwara.domain.model.TradeType

object TradeMapper {
    fun toDomain(entity: TradeEntity): Trade {
        return Trade(
            id = entity.id,
            accountId = entity.accountId,
            symbol = entity.symbol,
            type = TradeType.valueOf(entity.type),
            entryPrice = entity.entryPrice,
            exitPrice = entity.exitPrice,
            currentPrice = entity.currentPrice,
            lotSize = entity.lotSize,
            stopLoss = entity.stopLoss,
            takeProfit = entity.takeProfit,
            trailingStop = entity.trailingStop,
            profit = entity.profit,
            status = TradeStatus.valueOf(entity.status),
            openTime = entity.openTime,
            closeTime = entity.closeTime
        )
    }
    
    fun toEntity(domain: Trade): TradeEntity {
        return TradeEntity(
            id = domain.id,
            accountId = domain.accountId,
            symbol = domain.symbol,
            type = domain.type.name,
            entryPrice = domain.entryPrice,
            exitPrice = domain.exitPrice,
            currentPrice = domain.currentPrice,
            lotSize = domain.lotSize,
            stopLoss = domain.stopLoss,
            takeProfit = domain.takeProfit,
            trailingStop = domain.trailingStop,
            commission = 0.0,
            swap = 0.0,
            profit = domain.profit,
            status = domain.status.name,
            openTime = domain.openTime,
            closeTime = domain.closeTime,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
    }
}
